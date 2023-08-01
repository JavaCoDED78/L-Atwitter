package com.gmail.javacoded78.common.dto.common_new;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.javacoded78.common.dto.ImageResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatTweetResponse {
    private Long id;
    private String text;
    private LocalDateTime dateTime;
    private TweetUserResponse user;
    @JsonProperty("isDeleted")
    private boolean isDeleted;

    @Data
    static class TweetUserResponse {
        private Long id;
        private String fullName;
        private String username;
        private ImageResponse avatar;
    }
}
