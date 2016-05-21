package com.monogram.vertx.gremlin.impl;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import com.monogram.vertx.gremlin.GraphMessage;
import com.monogram.vertx.gremlin.VertxGremlinClient;
import com.monogram.vertx.gremlin.VertxGremlinService;

public class VertxGremlinServiceImpl implements VertxGremlinService {

    private final VertxGremlinClient client;

    public VertxGremlinServiceImpl(VertxGremlinClient client) {
        this.client = client;
    }

    public void close() {

    }

    public VertxGremlinClient create(Vertx vertx, JsonObject config) {
        return null;
    }

    @Override
    @Fluent
    public VertxGremlinService submitGremlin(GraphMessage GraphMessage, Handler<AsyncResult<String>> resultHandler) {
        client.submitGremlin(GraphMessage, resultHandler);
        return this;
    }
}
