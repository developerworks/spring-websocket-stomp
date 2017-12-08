package com.developertack.config;

import com.developertack.listener.WebSocketConnectListener;
import com.developertack.listener.WebSocketDisconnectListener;
import com.developertack.repository.ActiveWebSocketUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.session.Session;

/**
 * 连接处理器
 * <p>
 * 1. 处理客户端连接事件
 * 2. 处理客户端断开事件
 */
@Configuration
public class WebSocketHandlersConfig<S extends Session> {

    @Bean
    public WebSocketConnectListener<S> webSocketConnectListener(SimpMessageSendingOperations template, ActiveWebSocketUserRepository repository) {
        return new WebSocketConnectListener<>(template, repository);
    }

    @Bean
    public WebSocketDisconnectListener<S> webSocketDisconnectListener(SimpMessageSendingOperations template, ActiveWebSocketUserRepository repository) {
        return new WebSocketDisconnectListener<>(template, repository);
    }
}
