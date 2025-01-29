package com.example.demo.configs;

import com.example.demo.models.ChatMessage;
import com.example.demo.models.MessageType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            ChatMessage chatMessage = ChatMessage.builder()
                    .message(username + " has left the chat.")
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .timestamp(LocalDateTime.now())
                    .build();

            messageSendingOperations.convertAndSend("/topic/messages", chatMessage);
        }
    }
}