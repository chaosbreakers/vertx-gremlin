package com.monogram.metagraph.vertx.gremlin.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * vertex gremlin client that be used to communicate with gremlin server by event bus.
 *
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/21.
 */
public class VertexGremlinClient extends AbstractVerticle {

    private String eventBusAddress;
    private EventBus eventBus;

    /**
     * the two Method :{@linkplain VertexGremlinClient#sendMessagge(GremlinScriptMessage, Handler)}
     * and {@linkplain VertexGremlinClient#sendMessagge(String, Handler)} can be used after this verticle
     * deploy succeed and the {@code canBeUse} become {@<code>true</code>}
     */
    private boolean canBeUse = false;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        if (startFuture.succeeded()) {
            JsonObject config = config();
            eventBusAddress = config.getString("eventbusAddress");
            eventBus = vertx.eventBus();
            this.canBeUse = true;
        }
    }


    /**
     * send a gremlin script message to event bus.
     *
     * @param gremlinScript gremlin script, for example : g.v()
     * @param replyHandler  the handler for the replied result message.
     */
    public void sendMessagge(String gremlinScript, Handler<AsyncResult<Message<String>>> replyHandler) {
        if (canBeUse) {
            eventBus.send(eventBusAddress, gremlinScript, replyHandler);
        }
    }

    /**
     * send a gremlin script message to event bus.
     *
     * @param message      {@linkplain GremlinScriptMessage}
     * @param replyHandler the handler for the replied result message.
     */
    public void sendMessagge(GremlinScriptMessage message, Handler<AsyncResult<Message<String>>> replyHandler) {
        if (canBeUse) {
            String encodeMessage = Json.encode(message);
            eventBus.send(eventBusAddress, encodeMessage, replyHandler);
        }
    }
}
