package com.example.demo.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class ChatMessage {
    private String message;
    private String sender;
    private MessageType type;
    private LocalDateTime timestamp;

    public static ChatMessage buildChatMessage(String message, String sender, MessageType type) {
        return new ChatMessage(message, sender, type, LocalDateTime.now());
    }

}