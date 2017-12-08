package com.developertack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * 1. 对于在Websocket中支持会话来说, 我们继承的类是 AbstractSessionWebSocketMessageBrokerConfigurer 而不是 AbstractWebSocketMessageBrokerConfigurer.
 * 2. 把方法 registerStompEndpoints 重命名为 configureStompEndpoints
 *
 *
 */
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketSessionConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession> {
    /**
     * 设置Websocket 服务器监听的端点地址, 客户端可以通过如下方式进行了链接
     * <pre>
     * var socket = new Websocket('ws://localhost/messages');
     * ws = Stomp.over(socket);
     *
     * //usernaeme and password
     * ws.connect({}, function(frame){
     *     ws.subscribe("/topic/reply", function(msg) {
     *         console.log(msg);
     *         console.log(msg.body);
     *     });
     * });
     * </pre>
     *
     * @param registry
     */
    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/messages").withSockJS();
    }

    /**
     * 配置消息代理
     *
     * 设置服务器端可以使用的目标前缀, 设置应用程序目标前缀(广播消息), 设置用户目标前缀(单播消息)
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue/", "/topic/", "/user/");
        registry.setApplicationDestinationPrefixes("/app/");
        registry.setUserDestinationPrefix("/user/");
    }
}
