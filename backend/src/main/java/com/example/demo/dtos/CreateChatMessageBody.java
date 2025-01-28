package com.example.demo.dtos;

import com.example.demo.models.MessageType;
import lombok.Getter;


@Getter
public class CreateChatMessageBody {
    public String message;
    public String sender;
    public MessageType type;
}
