package com.monogram.vertx.gremlin;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

import com.monogram.vertx.gremlin.impl.VertxGremlinClientImpl;

@ProxyGen
@VertxGen
public interface VertxGremlinClient {

    public static final String SERVICE_ADDRESS = "com.monogram.vertx.gremlin:address";

    static VertxGremlinClient create(JsonObject config) {
        return new VertxGremlinClientImpl(config);
    }

    static VertxGremlinClient create(JsonObject config, String ip, String host) {
        return new VertxGremlinClientImpl(config, ip, host);
    }

    static VertxGremlinClient createProxy(Vertx vertx, String address) {
        return ProxyHelper.createProxy(VertxGremlinClient.class, vertx, address);
    }

    void execute(JsonObject document, Handler<AsyncResult<JsonObject>> resultHandler);
}
