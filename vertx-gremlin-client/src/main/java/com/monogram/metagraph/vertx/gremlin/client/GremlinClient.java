package com.monogram.metagraph.vertx.gremlin.client;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

/**
 * A Vert.x service used to interact with Gremlin server instances.
 *
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/23.
 */
public interface GremlinClient {

    static GremlinClient createClient(Vertx vertx, JsonObject config) {
        return new GremlinClientImp(vertx, config);
    }

    static GremlinClient createClient(Vertx vertx, JsonObject config, String host, int port) {
        return new GremlinClientImp(vertx, config, host, port);
    }

    GremlinClient execute(String json,Handler<Buffer> handler);

    GremlinClient execute(GremlinScriptMessage message,Handler<Buffer> handler);

    static void close(){
    }


}
