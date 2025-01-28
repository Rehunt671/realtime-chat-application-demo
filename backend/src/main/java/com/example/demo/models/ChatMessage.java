package com.example.demo.models;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class ChatMessage {
    public String message;
    public String sender;
    public MessageType type;
    public LocalDateTime timestamp;
}