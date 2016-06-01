package com.monogram.metagraph.vertx.gremlin.server.verticle;

import com.monogram.metagraph.vertx.gremlin.server.model.GremlinMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;

/**
 * GremlinClient for VertxGremlinServer.
 *
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/31.
 */
public class GremlinClient extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(GremlinClient.class);

    static {
        InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
    }

    public static void main(String[] args) throws InterruptedException {
        VertxOptions vertxOptions = new VertxOptions().setClustered(true);
        Vertx.clusteredVertx(vertxOptions, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(new GremlinClient());
            } else {
                res.cause().printStackTrace();
            }
        });

    }

    @Override
    public void start() throws Exception {
        EventBus eb = vertx.eventBus();
        vertx.setPeriodic(1000, v -> {
            eb.send("outMessage", createGremlinMessage(), reply -> {
                if (reply.succeeded()) {
                    Message<Object> result = reply.result();
                    Object body = result.body();
                    String res = (String)body;
                    logger.info(res);
                }
            });
        });
    }

    private String createGremlinMessage() {
        GremlinMessage gremlinMessage = new GremlinMessage();
//        gremlinMessage.setGremlinScript("g.V().count()");
        gremlinMessage.setGremlinScript("g.V()");
        return Json.encode(gremlinMessage);
    }

    private String createGraph() {
        GremlinMessage gremlinMessage = new GremlinMessage();
        gremlinMessage.setGremlinScript("graph = TinkerFactory.createModern()");
        return Json.encode(gremlinMessage);
    }

    private String createG() {
        GremlinMessage gremlinMessage = new GremlinMessage();
        gremlinMessage.setGremlinScript("g = graph.traversal(standard())");
        return Json.encode(gremlinMessage);
    }
}

