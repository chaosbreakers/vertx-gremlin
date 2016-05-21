package com.monogram.vertx.gremlin;

import static com.monogram.metagraph.titan.worker.TraversalAlias.getOLTPTraversalAlias;
import static com.monogram.metagraph.util.Metagraph.METAGRAPH;
import static com.monogram.metagraph.util.Metagraph.l_GRAPH;
import static com.monogram.metagraph.util.Metagraph.l_HAS_GRAPH;
import static com.monogram.metagraph.util.Metagraph.p_graph_config;
import static com.monogram.metagraph.util.Metagraph.p_graph_id;
import static com.monogram.metagraph.util.Metagraph.p_graph_status;

import com.monogram.metagraph.exception.ConfigException;
import com.monogram.metagraph.message.GraphAction;
import com.monogram.metagraph.message.GraphMessage;
import com.monogram.metagraph.util.Configurations;
import com.monogram.metagraph.util.GremlinScript;
import com.monogram.metagraph.util.Metagraph;

import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.server.Settings;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public enum TitanWorker {

    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(TitanWorker.class);

    private Settings settings = null;
    private TitanConnection connection = null;
    private TitanServer server = null;

    private boolean hasInit = false;

    /**
     * start an embed titan server
     *
     * @param configFile server setting yaml
     * @throws Exception
     */
    public synchronized void start(String configFile) throws Exception {
        if (!hasInit) {
            logger.info("start titan server from {}", configFile);
            try {
                settings = Settings.read(configFile);
            } catch (Exception ex) {
                logger.error("Configuration file at {} could not be found or parsed properly. [{}]", configFile, ex.getMessage());
            }

            try {
                server = new TitanServer(settings).start();
            } catch (Exception e) {
                logger.error("Can't start metagraph server", e.getMessage());
                throw new ConfigException("Metagraph Server start failed", e);
            }

            logger.info("start titan connection");
            connection = new TitanConnection().init();

            logger.info("load all the graphs in metagraph");
            loadGraphs();
            hasInit = true;
        }
    }

    /**
     * @param graphId
     * @param configuration
     */
    public void createGraph(String graphId, Configuration configuration) throws Exception {
        server.createGraph(graphId, configuration);
    }

    public void deleteGraph(String graphId) throws Exception {
        server.deleteGraph(graphId);
    }

    public void stop() {
        connection.close();
        server.stop();
    }

    /**
     * sync execute an query
     *
     * @param graphMessage
     * @return
     * @throws Exception
     */
    public List<Object> execute(GraphMessage graphMessage) throws Exception {
        return connection.submit(graphMessage.getGremlin(), graphMessage.isUseMetagraph() ? null : getAlias(graphMessage), graphMessage.getBindings());
    }

    /**
     * async execute an query
     *
     * @param graphMessage
     * @return
     */
    public CompletableFuture<ResultSet> executeAsync(GraphMessage graphMessage) throws Exception {
        return connection.submitAsync(graphMessage.getGremlin(), graphMessage.isUseMetagraph() ? null : getAlias(graphMessage), graphMessage.getBindings());
    }

    protected TitanServer getServer() {
        return server;
    }

    protected TitanConnection getConnection() {
        return connection;
    }
    
    private void loadGraphs() throws Exception {
        List<Object> result = queryGraphs();
        result.stream().parallel().forEach(obj -> loadGraph(Json.encode(obj)));
        logger.info("Titan Worker started");
    }

    public List<Object> queryGraphs() throws Exception {
        GraphMessage graphMessage = new GraphMessage();
        graphMessage.setGraphId(METAGRAPH);
        graphMessage.setAsync(false);
        graphMessage.setGraphAction(GraphAction.OLTP);
        GremlinScript gremlinScript = new GremlinScript();
        Set<String> values = new HashSet<>();
        values.add(p_graph_id);
        values.add(p_graph_status);
        values.add(p_graph_config);
        String script = gremlinScript.V()
                                     .hasLabel(l_GRAPH)
                                     .has(p_graph_id, METAGRAPH)
                                     .outE(l_HAS_GRAPH)
                                     .inV()
                                     .valueMap(values)
                                     .build();
        graphMessage.setGremlin(script);
        List<Object> result = execute(graphMessage);
        return result;
    }


    /**
     * 加载图
     *
     * @param json
     */
    private void loadGraph(String json) {
        JsonObject jsonObject = new JsonObject(json);
        String graphId = jsonObject.getJsonArray(Metagraph.p_graph_id).getString(0);// JSON
        // Array
        String graphStatus = jsonObject.getJsonArray(Metagraph.p_graph_status).getString(0);
        if ("deleted".equalsIgnoreCase(graphStatus)) {
            logger.info("Graph[{}] has deleted", graphId);
            return;
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> graphConfig = Json.decodeValue(jsonObject.getJsonArray(Metagraph.p_graph_config).getString(0), Map.class);
        server.addGraph(graphId, Configurations.start().addConfig(graphConfig).build());
        logger.info("graph[{}] is loaded.", graphId);
    }

    public Graph getGraphById(String graphId) {
        return server.getGraphManager().getGraphs().get(graphId);
    }

    private String getAlias(GraphMessage graphMessage) {
        // metagraph was global
        if (graphMessage.getGraphId() == null || graphMessage.getGraphId().equals(METAGRAPH)) {
            return null;
        }
        switch (graphMessage.getGraphAction()) {
            case OLAP: {
                throw new UnsupportedOperationException("OLAP use another service to progress");
            }
            default:
                return getOLTPTraversalAlias(graphMessage.getGraphId());
        }
    }
}
