package com.developertack.listener;

import com.developertack.repository.ActiveWebSocketUserRepository;
import com.developertack.repository.entity.ActiveWebSocketUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.security.Principal;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Websocket 会话连接事件监听器, 可以在客户端建立连接时执行:
 * <p>
 * 1. 客户端状态的初始化操作, 比如存储用户的会话数据
 * 2. 记录连接日志
 * 3.
 */
public class WebSocketConnectListener<S> implements ApplicationListener<SessionConnectEvent> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConnectListener.class);

    private ActiveWebSocketUserRepository repository;

    private SimpMessageSendingOperations messageSendingOperations;

    public WebSocketConnectListener(SimpMessageSendingOperations messageSendingOperations, ActiveWebSocketUserRepository repository) {
        this.messageSendingOperations = messageSendingOperations;
        this.repository = repository;

    }

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        // 消息头
        MessageHeaders headers = event.getMessage().getHeaders();
        // 用户信息
        Principal user = SimpMessageHeaderAccessor.getUser(headers);
        if (user == null) {
            return;
        }
        String id = SimpMessageHeaderAccessor.getSessionId(headers);
        logger.info("Session ID: " + id);

        repository.save(new ActiveWebSocketUser(id, user.getName(), Calendar.getInstance()));
        messageSendingOperations.convertAndSend("/topic/friends/signin", Arrays.asList(user.getName()));
    }
}
