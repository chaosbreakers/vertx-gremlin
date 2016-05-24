package com.monogram.metagraph.vertx.gremlin.client;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Created by zhaoliang{weston_contribute@163.com} on 2016/5/24.
 */
public class Test {

    public static void main(String[] args) {
        GremlinClient client = GremlinClient.createClient(Vertx.vertx(), new JsonObject(), "localhost", 12345);

//        client.close();
        client.execute("{name:\"haha\"}", event -> {
            System.out.println(event.toString());
        });

//        for(int i = 0; i < 10000; i++){
//            client.execute("{name:\"haha\"} " + i, event -> {
//                System.out.println(event.toString());
//            });
//        }


//        client.execute("{name:\"hehe\"}", new Handler<Buffer>() {
//            @Override
//            public void handle(Buffer event) {
//                System.out.println(event.toString());
//            }
//        });
    }

}
