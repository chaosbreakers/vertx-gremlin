package com.monogram.vertx.gremlin.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import com.monogram.vertx.gremlin.GraphMessage;
import com.monogram.vertx.gremlin.VertxGremlinClient;

public class VertxGremlinClientImpl implements VertxGremlinClient {

    public VertxGremlinClientImpl(Vertx vertx, JsonObject config) {
    }

    @Override
    public VertxGremlinClient submitGremlin(GraphMessage GraphMessage, Handler<AsyncResult<String>> resultHandler) {
        return this;
    }

    

}
