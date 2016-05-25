package com.monogram.metagraph.vertx.gremlin.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.streams.Pump;

/**
 * Created by zhaoliang{weston_contribute@163.com} on 2016/5/24.
 */
public class Server extends AbstractVerticle {

    // Convenience method so you can run it in your IDE
    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {

        vertx.createNetServer().connectHandler(sock -> {

            // Create a pump
            Pump.pump(sock, sock).start();

        }).listen(12345);

        System.out.println("Echo server is now listening");

    }
}
