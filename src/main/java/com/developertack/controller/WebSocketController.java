package com.developertack.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WebSocketController {

    @Autowired
//	private SimpMessageSendingOperations messagingTemplate;
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    public void processMessageFromClient(@Payload String message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        System.out.println(sessionId);
        messagingTemplate.convertAndSend("/topic/reply", new Gson().fromJson(message, Map.class).get("name"));

    }

    @MessageMapping("/room/greeting/{room}")
    public Greeting greet(@DestinationVariable String room, HelloMessage message) throws Exception {
        Greeting greeting = new Greeting("Hello, " + message.getName() + "!");
        messagingTemplate.convertAndSend("/topic/room/" + room, greeting);
        return greeting;
    }
}
