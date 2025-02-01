package com.example.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registers the /ws endpoint, enabling SockJS fallback options
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefixes of the destinations that clients can subscribe to /topic or /queue (e.g. http://localhost:8080/topic/messages).
        registry.enableSimpleBroker( "/topic","/queue");
        // /app: Used for client-to-server requests (e.g., sending messages via @MessageMapping) (e.g. http://localhost:8080/app/chat/sendMessage).
        registry.setApplicationDestinationPrefixes("/app");

    }
}