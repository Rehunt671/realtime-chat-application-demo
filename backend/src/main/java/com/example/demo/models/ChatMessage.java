package com.example.demo.models;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class ChatMessage {
    private String message;
    private String sender;
    private MessageType type;
    private LocalDateTime timestamp;
}