package com.gmail.javacoded78.dto.response.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatTweetResponse {

    private Long id;
    private String text;
    private LocalDateTime dateTime;
    private TweetUserResponse user;
    @JsonProperty("isDeleted")
    private boolean isDeleted;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(of = "id")
    static class TweetUserResponse {

        private Long id;
        private String fullName;
        private String username;
        private String avatar;
    }
}
