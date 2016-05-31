package com.monogram.metagraph.vertx.gremlin.server.model;

import java.util.Map;

/**
 * the object of Gremlin Message.
 *
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/30.
 */
public class GremlinMessage {
    private String graphId;
    private String gremlinScript;
    private Map<String, Object> parambindings;

    public String getGraphId() {
        return graphId;
    }

    public void setGraphId(String graphId) {
        this.graphId = graphId;
    }

    public String getGremlinScript() {
        return gremlinScript;
    }

    public void setGremlinScript(String gremlinScript) {
        this.gremlinScript = gremlinScript;
    }

    public Map<String, Object> getParambindings() {
        return parambindings;
    }

    public void setParambindings(Map<String, Object> parambindings) {
        this.parambindings = parambindings;
    }
}
