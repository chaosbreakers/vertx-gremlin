package com.monogram.metagraph.vertx.gremlin.client;

import static java.util.Objects.requireNonNull;

import org.apache.avro.io.JsonEncoder;

import java.util.Objects;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetSocket;

/**
 * The implementation of the {@link GremlinClient}. This implementation is based on the async driver
 * provided by Gremlin server.
 *
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/23.
 */
public class GremlinClientImp implements GremlinClient {
    private static final Logger log = LoggerFactory.getLogger(GremlinClientImp.class);

    private final Vertx vertx;
    private final String host;
    private final int port;

    public GremlinClientImp(Vertx vertx, JsonObject config) {
        Objects.requireNonNull(vertx);
        Objects.requireNonNull(config);
        this.vertx = vertx;
        this.host = config.getString("gremlinclient.host", "localhost");
        this.port = config.getInteger("gremlinclient.port", 10180);
    }

    public GremlinClientImp(Vertx vertx, JsonObject config, String host, int port) {
        Objects.requireNonNull(vertx);
        Objects.requireNonNull(config);
        requireNonNull(host, "host cannot be null");
        requireNonNull(port, "port cannot be null");
        this.vertx = vertx;
        this.host = host;
        this.port = port;
    }

    @Override
    public GremlinClient execute(String json,Handler<Buffer> handler) {
        vertx.createNetClient().connect(port, host, res -> {
            if(res.succeeded()){
                NetSocket socket = res.result();
                socket.write(json);
                socket.handler(handler);
                socket.close();
            }
            System.out.println("hello");
        });
        return this;
    }

    @Override
    public GremlinClient execute(GremlinScriptMessage message,Handler<Buffer> handler) {
        vertx.createNetClient().connect(port, host, res -> {
            if(res.succeeded()){
                NetSocket socket = res.result();
                socket.write(Json.encode(message));
                socket.handler(handler);
                socket.close();
            }
            System.out.println("hello");
        });
        return this;
    }
}
