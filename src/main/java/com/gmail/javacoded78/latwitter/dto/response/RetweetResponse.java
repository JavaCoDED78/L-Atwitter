package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RetweetResponse {

    private Long id;
    private LocalDateTime retweetDate;
    private UserResponse user;
    private TweetResponseCommon tweet;
}
