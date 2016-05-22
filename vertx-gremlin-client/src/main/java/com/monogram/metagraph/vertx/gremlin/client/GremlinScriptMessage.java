package com.monogram.metagraph.vertx.gremlin.client;

import java.util.Map;

/**
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/21.
 */
public class GremlinScriptMessage {

    private String gremlinScript;
    private Map<String, String> parambindings;
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
