package com.monogram.metagraph.vertx.gremlin.server.verticle;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.driver.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/27.
 */
public enum Client {

    single;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected org.apache.tinkerpop.gremlin.driver.Client.ClusteredClient client;
    protected Cluster cluster;


    public Client start() {
        cluster = Cluster.build().serializer(Serializers.GRAPHSON).create();
        client = cluster.connect();
        client.init();
        return this;
    }

    public void close() {
        client.close();
    }

    public List<Object> submit(String gremlin) throws Exception {
        return submit(gremlin, null, null);
    }

    public List<Object> submit(String gremlin, String alias, Map<String, Object> bindings) throws Exception {
        logger.info("execute script:[" + gremlin + "][" + bindings + "]");
        List<Result> results = client.submit(gremlin, alias, bindings).all().join();
        return results.stream().map(Result::getObject).collect(Collectors.toList());
    }

    public CompletableFuture<ResultSet> submitAsync(String gremlin, String alias, Map<String, Object> bindings) {
        return client.submitAsync(gremlin, alias, bindings);
    }
}
