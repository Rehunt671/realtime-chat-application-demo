package com.example.demo.controllers;
import com.example.demo.dtos.CreateChatMessageBody;
import com.example.demo.models.ChatMessage;
import com.example.demo.models.MessageType;
import com.example.demo.models.User;
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

    @MessageMapping("/chat/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(CreateChatMessageBody createChatMessageBody) {
        String message = createChatMessageBody.getMessage();
        String username = createChatMessageBody.getSender();
        MessageType messageType = createChatMessageBody.getType();
        return ChatMessage.buildChatMessage(message, username, messageType);
    }

    @MessageMapping("/chat/addUser")
    @SendToUser("/queue/connected")
    public User addUser(CreateChatMessageBody createChatMessageBody, SimpMessageHeaderAccessor headerAccessor) {
        String message = createChatMessageBody.getMessage();
        String username = createChatMessageBody.getSender();
        MessageType messageType = createChatMessageBody.getType();
        headerAccessor.getSessionAttributes().put("username", username);
        messagingTemplate.convertAndSend("/topic/messages", ChatMessage.buildChatMessage(message, username, messageType));
        return new User(username);
    }
}