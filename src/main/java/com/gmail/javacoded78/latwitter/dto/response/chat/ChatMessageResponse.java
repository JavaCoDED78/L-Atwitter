package com.gmail.javacoded78.latwitter.dto.response.chat;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponse {

    private Long id;
    private String text;
    private LocalDateTime date;
    private ChatTweetResponse tweet;
    private ChatUserResponse author;
    private ChatResponse chat;
}
