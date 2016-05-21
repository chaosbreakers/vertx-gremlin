package com.monogram.vertx.gremlin;

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ProxyHelper;

import com.monogram.vertx.gremlin.impl.VertxGremlinClientImpl;

public class VertxGremlinVerticle extends AbstractVerticle {

    VertxGremlinClient service;

    @Override
    public void start() throws Exception {
        service = new VertxGremlinClientImpl(config());
        ProxyHelper.registerService(VertxGremlinClient.class, vertx, service, VertxGremlinClient.SERVICE_ADDRESS);
    }

}
