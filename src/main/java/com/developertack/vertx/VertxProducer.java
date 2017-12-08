package com.developertack.vertx;

import com.hazelcast.core.HazelcastInstance;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A component managing the lifecycle of the clustered Vert.x instance.
 * It uses the Spring-managed {@link HazelcastInstance}.
 * <p>
 * This bean is created <strong>after</strong> and destroyed <strong>before</strong> the {@link HazelcastInstance}.
 *
 * @author Thomas Segismont
 */
@Component
public class VertxProducer {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    private Vertx vertx;

    @PostConstruct
    void init() throws ExecutionException, InterruptedException {
        VertxOptions options = new VertxOptions()
            .setClusterManager(new HazelcastClusterManager(hazelcastInstance));
        CompletableFuture<Vertx> future = new CompletableFuture<>();
        Vertx.clusteredVertx(options, ar -> {
            if (ar.succeeded()) {
                future.complete(ar.result());
            } else {
                future.completeExceptionally(ar.cause());
            }
        });
        vertx = future.get();
    }

    /**
     * Exposes the clustered Vert.x instance.
     * We must disable destroy method inference, otherwise Spring will call the {@link Vertx#close()} automatically.
     */
    @Bean(destroyMethod = "")
    Vertx vertx() {
        return vertx;
    }

    @PreDestroy
    void close() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = new CompletableFuture<>();
        vertx.close(ar -> future.complete(null));
        future.get();
    }
}