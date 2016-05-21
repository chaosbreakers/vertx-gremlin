package com.monogram.vertx.gremlin;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;


/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
@ProxyGen
@VertxGen
public interface VertxGremlinService extends VertxGremlinClient {

    public void close();

 
}
