package com.monogram.metagraph.vertx.gremlin.client;

import java.util.Map;

/**
 * the message object submitted by client.
 *
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/21.
 */
public class GremlinScriptMessage {

    /**
     * gremlin language script.
     */
    private String gremlinScript;

    /**
     * some parameters binding in gremlin script.
     */
    private Map<String, String> parambindings;

    /**
     * the id of graph in which gremlin script executed.
     */
    private String graphId;

    public String getGremlinScript() {
        return gremlinScript;
    }

    public void setGremlinScript(String gremlinScript) {
        this.gremlinScript = gremlinScript;
    }

    public Map<String, String> getParambindings() {
        return parambindings;
    }

    public void setParambindings(Map<String, String> parambindings) {
        this.parambindings = parambindings;
    }

    public String getGraphId() {
        return graphId;
    }

    public void setGraphId(String graphId) {
        this.graphId = graphId;
    }
}
