package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.dto.response.chat.ChatTweetResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageResponse {

    private Long id;
    private String text;
    private LocalDateTime date;
    private Long authorId;
    private ChatTweetResponse tweet;
    private ChatResponse chat;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatResponse {

        private Long id;
    }
}
