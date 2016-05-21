package com.monogram.vertx.gremlin;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import com.monogram.vertx.gremlin.impl.VertxGremlinClientImpl;

@VertxGen
public interface VertxGremlinClient {

    static VertxGremlinClient create(Vertx vertx, JsonObject config) {
        return new VertxGremlinClientImpl(vertx, config);
    }

    @Fluent
    public VertxGremlinClient submitGremlin(GraphMessage GraphMessage, Handler<AsyncResult<String>> resultHandler);
}
