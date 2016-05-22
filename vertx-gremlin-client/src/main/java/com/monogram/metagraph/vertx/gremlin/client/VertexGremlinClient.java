package com.monogram.metagraph.vertx.gremlin.client;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;

/**
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/21.
 */
@VertxGen
public class VertexGremlinClient {

    private NetServer server;

    public NetServer connect(Vertx vertx, JsonObject config, String host, int port) {
        return vertx.createNetServer().listen(port, host);
    }

    public void close() {
        server.close();
    }

    public Object execute(GremlinScriptMessage message) {
        return null;
    }

    public Object execute(String gremlinScriptMessageJson){
        return null;
    }

}
