package com.gmail.javacoded78.latwitter.dto.request;

import lombok.Data;

@Data
public class ChatMessageRequest {

    private Long chatId;
    private String text;
}