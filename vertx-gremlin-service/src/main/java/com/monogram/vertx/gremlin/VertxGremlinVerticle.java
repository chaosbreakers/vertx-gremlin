package com.monogram.vertx.gremlin;

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ProxyHelper;

import com.monogram.vertx.gremlin.impl.VertxGremlinServiceImpl;

public class VertxGremlinVerticle  extends AbstractVerticle {

    VertxGremlinService service;

    @Override
    public void start() throws Exception {

      service = new VertxGremlinServiceImpl(VertxGremlinClient.create(vertx, config()));

      String address = config().getString("address");
      if (address == null) {
        throw new IllegalStateException("address field must be specified in config for client verticle");
      }
      ProxyHelper.registerService(VertxGremlinService.class, vertx, service, address);
    }

    @Override
    public void stop() throws Exception {
      service.close();
    }
}
