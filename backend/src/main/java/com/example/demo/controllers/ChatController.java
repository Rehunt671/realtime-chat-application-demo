package com.example.demo.controllers;

import com.example.demo.dtos.CreateChatMessageBody;
import com.example.demo.models.ChatMessage;
import com.example.demo.models.User;
import com.example.demo.repositories.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRepository chatRepository;

    @MessageMapping("/chat/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(CreateChatMessageBody createChatMessageBody) {
        return chatRepository.buildChatMessageFromCreateChatMessageBody(createChatMessageBody);
    }

    @MessageMapping("/chat/addUser")
    @SendToUser("/queue/connected")
    public User addUser(CreateChatMessageBody createChatMessageBody, SimpMessageHeaderAccessor headerAccessor) {
        String username = createChatMessageBody.getSender();
        headerAccessor.getSessionAttributes().put("username", username);
        messagingTemplate.convertAndSend("/topic/messages", chatRepository.buildChatMessageFromCreateChatMessageBody(createChatMessageBody));

        return new User(username);
    }
}