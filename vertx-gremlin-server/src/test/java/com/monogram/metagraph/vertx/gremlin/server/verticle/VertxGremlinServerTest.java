package com.monogram.metagraph.vertx.gremlin.server.verticle;

import static org.junit.Assert.*;

import org.junit.Test;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

/**
 * VertxGremlinServer Test.
 *
 * Created by zhaoliang(weston_contribute@163.com) on 2016/5/31.
 */
public class VertxGremlinServerTest {

    @Test
    public void name() throws Exception {
        JsonObject config = new JsonObject()
            .put("gremlinServerConfigPath", "D:\\tg-metagraph-reposities\\vertx-gremlin\\vertx-gremlin-server\\src\\test\\resources\\gremlin-server.yaml")
            .put("eventBusAddress", "outMessage");
        DeploymentOptions options = new DeploymentOptions().setConfig(config);
        VertxOptions vertxOptions = new VertxOptions().setClustered(true);
        Vertx.clusteredVertx(vertxOptions, event -> {
            if (event.succeeded()) {
                Vertx vertx = event.result();
                vertx.deployVerticle(new VertxGremlinServer(), options);
            }
        });

        Thread.sleep(Long.MAX_VALUE);
    }
}