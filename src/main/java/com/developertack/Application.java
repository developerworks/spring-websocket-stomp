package com.developertack;

import com.developertack.vertx.HelloVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/**
 * Vert.x 整合 Spring Boot 实现REST服务
 * http://www.jianshu.com/p/cda203ffd23e
 */
@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private Vertx vertx;

    @Autowired
    private HelloVerticle helloVerticle;

    @Value("${systemProperties.httpPort:#{null}}")
    private Integer port;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Deploy verticles when the Spring application is ready.
     */
    @EventListener
    void deployVerticles(ApplicationReadyEvent event) {
        logger.info("Deploying verticles...");
        vertx.deployVerticle(helloVerticle);
//        if (port != null) {
//            JsonObject config = new JsonObject().put("port", port);
//            vertx.deployVerticle(new HttpVerticle(), new DeploymentOptions().setConfig(config));
//        }
    }
}
