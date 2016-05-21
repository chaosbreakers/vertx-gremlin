/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.monogram.vertx.gremlin.groovy;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.vertx.groovy.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
@CompileStatic
public class VertxGremlinClient {
  private final def com.monogram.vertx.gremlin.VertxGremlinClient delegate;
  public VertxGremlinClient(Object delegate) {
    this.delegate = (com.monogram.vertx.gremlin.VertxGremlinClient) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public static VertxGremlinClient create(Vertx vertx) {
    def ret= InternalHelper.safeCreate(com.monogram.vertx.gremlin.VertxGremlinClient.create((io.vertx.core.Vertx)vertx.getDelegate()), com.monogram.vertx.gremlin.groovy.VertxGremlinClient.class);
    return ret;
  }
  public static VertxGremlinClient createProxy(Vertx vertx, String address) {
    def ret= InternalHelper.safeCreate(com.monogram.vertx.gremlin.VertxGremlinClient.createProxy((io.vertx.core.Vertx)vertx.getDelegate(), address), com.monogram.vertx.gremlin.groovy.VertxGremlinClient.class);
    return ret;
  }
  public void process(Map<String, Object> document, Handler<AsyncResult<Map<String, Object>>> resultHandler) {
    this.delegate.process(document != null ? new io.vertx.core.json.JsonObject(document) : null, new Handler<AsyncResult<io.vertx.core.json.JsonObject>>() {
      public void handle(AsyncResult<io.vertx.core.json.JsonObject> event) {
        AsyncResult<Map<String, Object>> f
        if (event.succeeded()) {
          f = InternalHelper.<Map<String, Object>>result((Map<String, Object>)InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<Map<String, Object>>failure(event.cause())
        }
        resultHandler.handle(f)
      }
    });
  }
}
