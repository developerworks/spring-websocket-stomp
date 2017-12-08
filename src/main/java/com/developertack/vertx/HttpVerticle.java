package com.developertack.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerOptions;

/**
 * @author Thomas Segismont
 */
public class HttpVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        HttpServerOptions options = new HttpServerOptions().setPort(config().getInteger("port"));
        vertx.createHttpServer(options).requestHandler(request -> {
            String name = request.getParam("name");
            if (name == null) {
                request.response().setStatusCode(400).end("Missing name");
            } else {
                vertx.eventBus().<String>send("hello", name, ar -> {
                    if (ar.succeeded()) {
                        request.response().end(ar.result().body());
                    } else {
                        request.response().setStatusCode(500).end(ar.cause().getMessage());
                    }
                });
            }
        }).listen(ar -> {
            if (ar.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(ar.cause());
            }
        });
    }
}