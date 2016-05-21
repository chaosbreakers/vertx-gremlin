package com.monogram.vertx.gremlin.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.tinkerpop.gremlin.driver.message.ResponseMessage;
import org.apache.tinkerpop.gremlin.driver.simple.WebSocketClient;

import com.monogram.vertx.gremlin.VertxGremlinClient;

public class VertxGremlinClientImpl implements VertxGremlinClient {

    private JsonObject config;
    private String ip;
    private String port;

    private WebSocketClient webSocketClient = null;

    public VertxGremlinClientImpl(JsonObject config) {
        this.config = config;
    }

    public VertxGremlinClientImpl(JsonObject config, String ip, String port) {
        this.config = config;
        this.ip = ip;
        this.port = port;

    }

    @Override
    public void execute(JsonObject document, Handler<AsyncResult<JsonObject>> resultHandler) {
        AsyncResult<JsonObject> asyncResult = null;
        try {
            CompletableFuture<List<ResponseMessage>> completableFuture = gremlinServer().submitAsync(document.getString("gremlinScript"));
            asyncResult = buildResult(completableFuture.get().get(0).getResult().getData(), null, true);
        } catch (Exception e) {
            asyncResult = buildResult(null, e, true);
        }
        resultHandler.handle(asyncResult);
    }

    private AsyncResult<JsonObject> buildResult(Object object, Throwable throwable, boolean isSucceeded) {
        AsyncResult<JsonObject> asyncResult = new AsyncResult<JsonObject>() {

            @Override
            public JsonObject result() {
                return (JsonObject) object;
            }

            @Override
            public Throwable cause() {
                return throwable;
            }

            @Override
            public boolean succeeded() {
                return isSucceeded ? true : false;
            }

            @Override
            public boolean failed() {
                return isSucceeded ? false : true;
            }
        };
        return asyncResult;
    }

    private WebSocketClient gremlinServer() {
        if (webSocketClient == null) {
            try {
                webSocketClient = new WebSocketClient(new URI("ws://" + ip + ":" + port));
            } catch (URISyntaxException e) {

            }
        }
        return webSocketClient;
    }
}
