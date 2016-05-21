/*
 * Copyright (c) 2016 monogram
 */

package com.monogram.vertx.gremlin;

import java.util.Map;

public class GraphMessage {

    private String gremlin;
    private Map<String, Object> bindings;

    public GraphMessage() {
    }

    public Map<String, Object> getBindings() {
        return bindings;
    }

    public void setBindings(Map<String, Object> bindings) {
        this.bindings = bindings;
    }

    public String getGremlin() {
        return gremlin;
    }

    public void setGremlin(String gremlin) {
        this.gremlin = gremlin;
    }

}
