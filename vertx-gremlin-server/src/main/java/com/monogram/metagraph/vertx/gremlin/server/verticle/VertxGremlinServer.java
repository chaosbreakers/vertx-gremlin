package com.monogram.metagraph.vertx.gremlin.server.verticle;

import com.monogram.metagraph.vertx.gremlin.server.model.GremlinMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * the gremlin server that consumer event bus message operating metagraph.
 *
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/26.
 */
public class VertxGremlinServer extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(VertxGremlinServer.class);

    static {
        InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
    }

    private Client client;
    private String eventBusAddr;


    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        super.stop(stopFuture);
    }

    @Override
    public void start() throws Exception {
        JsonObject config = config();
        String gremlinServerConfigPath = config.getString("gremlinServerConfigPath");
        Server.single.init(gremlinServerConfigPath).start();
        client = Client.single.start();
        eventBusAddr = config.getString("eventBusAddress");

        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(eventBusAddr, event -> {
            String body = (String) event.body();
            logger.info("received from the eventbus of {} : {}", eventBusAddr, body);
            GremlinMessage gremlinMessage = Json.decodeValue(body, GremlinMessage.class);
            List<java.lang.Object> submit;
            try {
                submit = client.submit(gremlinMessage.getGremlinScript(), null, gremlinMessage.getParambindings());
                event.reply(Json.encode(submit));
            } catch (Exception e) {
                logger.error("can't execute script : " + gremlinMessage.getGremlinScript() + " because: " + e.getMessage(), e);
            }
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
