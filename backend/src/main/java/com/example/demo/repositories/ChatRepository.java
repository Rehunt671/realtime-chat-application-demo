package com.example.demo.repositories;


import com.example.demo.dtos.CreateChatMessageBody;
import com.example.demo.models.ChatMessage;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ChatRepository {

    public ChatMessage buildChatMessageFromCreateChatMessageBody(CreateChatMessageBody createChatMessageBody) {
        return ChatMessage.builder()
                .message(createChatMessageBody.getMessage())
                .sender(createChatMessageBody.getSender())
                .type(createChatMessageBody.getType())
                .timestamp(LocalDateTime.now())
                .build();
    }

}
