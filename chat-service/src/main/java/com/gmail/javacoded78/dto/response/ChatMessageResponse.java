package com.gmail.javacoded78.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.dto.ChatTweetResponse;
import com.gmail.javacoded78.dto.ImageResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
