package com.developertack.listener;

import com.developertack.repository.ActiveWebSocketUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * 客户端连接断开监听器, 一般执行资源回收操作
 * <p>
 * 1. 销毁客户端会话数据
 * 2. 记录连接断开日志
 * 3. 记录连接时长
 * ....
 */
public class WebSocketDisconnectListener<S> implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketDisconnectListener.class);
    private SimpMessageSendingOperations messageSendingOperations;
    private ActiveWebSocketUserRepository repository;

    /**
     * 构造函数
     *
     * @param messageSendingOperations
     * @param repository
     */
    public WebSocketDisconnectListener(SimpMessageSendingOperations messageSendingOperations, ActiveWebSocketUserRepository repository) {
        this.messageSendingOperations = messageSendingOperations;
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        String id = event.getSessionId();
        if(id == null) {
            return;
        }
//        repository.findById();
    }
}
