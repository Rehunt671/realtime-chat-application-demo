package com.example.demo.configs;
import com.example.demo.models.ChatMessage;
import com.example.demo.models.MessageType;
import lombok.AllArgsConstructor;
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
            ChatMessage chatMessage = ChatMessage.buildChatMessage(username + " has left the chat.", username , MessageType.LEAVE);
            messageSendingOperations.convertAndSend("/topic/messages", chatMessage);
        }
    }
}