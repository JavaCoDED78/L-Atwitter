package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.dto.response.chat.ChatTweetResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponse {

    private Long id;
    private String text;
    private LocalDateTime date;
    private Long authorId;
    private ChatTweetResponse tweet;
    private ChatResponse chat;

    @Data
    static class ChatResponse {

        private Long id;
    }
}
