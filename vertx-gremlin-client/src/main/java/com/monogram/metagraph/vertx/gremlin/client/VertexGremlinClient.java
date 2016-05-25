package com.monogram.metagraph.vertx.gremlin.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

/**
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/21.
 */
public abstract class VertexGremlinClient extends AbstractVerticle {

    private NetClient client;
    private String host;
    private int port;
    private NetSocket socket;

    public VertexGremlinClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public VertexGremlinClient() {
        super();
    }

    @Override
    public void start() throws Exception {
        vertx.createNetClient().connect(port, host, res -> {

            if (res.succeeded()) {
                socket = res.result();
                socket.handler(buffer -> {
                    System.out.println("Net client receiving: " + buffer.toString("UTF-8"));
                });

                // Now send some data
                for (int i = 0; i < 10; i++) {
                    socket.write("test");
                }
            } else {
                System.out.println("Failed to connect : " + res.cause());
            }
        });
    }


    public NetServer connect(Vertx vertx, JsonObject config, String host, int port) {
        return vertx.createNetServer().listen(port, host);
    }

    public void close() {
        client.close();
    }

    public Object execute(GremlinScriptMessage message) {
        return null;
    }

    public Object execute(String gremlinScriptMessageJson) {
        return null;
    }

}
