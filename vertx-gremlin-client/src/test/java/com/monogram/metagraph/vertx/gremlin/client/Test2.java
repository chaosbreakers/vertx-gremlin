package com.monogram.metagraph.vertx.gremlin.client;

import java.util.function.Function;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

/**
 * Created by zhaoliang{weston_contribute@163.com} on 2016/5/24.
 */
public class Test2 {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();
        netClient.connect(12345, "localhost", handler -> {
            if (handler.succeeded()) {
                System.out.println("connected!");
                NetSocket socket = handler.result();
                socket.handler(buffer -> {
                    String result = buffer.toString();
                    System.out.println(result);
                    if (result.equals("hello")) {
                        socket.write("world");
                    } else if (result.equals("world")) {
                        socket.write("hello");
                    }
                });
                socket.write("hello");
            } else {
                handler.failed();
            }
        });
        netClient.close();
    }

}
