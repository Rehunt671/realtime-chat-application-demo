package com.example.demo.controllers;

import com.example.demo.dtos.CreateChatMessageBody;
import com.example.demo.models.ChatMessage;
import com.example.demo.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations messageSendingOperations;
    public ChatMessage createChatMessage(CreateChatMessageBody createChatMessageBody) {
        return ChatMessage.builder()
                .message(createChatMessageBody.getMessage())
                .sender(createChatMessageBody.getSender())
                .type(createChatMessageBody.getType())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @MessageMapping("/chat/sendMessage")
    @SendTo("/topic/room")
    public ChatMessage sendMessage(CreateChatMessageBody createChatMessageBody) {
        return createChatMessage(createChatMessageBody);
    }

    @MessageMapping("/chat/addUser")
    @SendTo("/topic/room")
    public ChatMessage addUser(CreateChatMessageBody createChatMessageBody, SimpMessageHeaderAccessor headerAccessor) {
        String username = createChatMessageBody.getSender();
        headerAccessor.getSessionAttributes().put("username", username);
        messageSendingOperations.convertAndSendToUser(username,"/topic/user", new User(username));
        return createChatMessage(createChatMessageBody);
    }
}