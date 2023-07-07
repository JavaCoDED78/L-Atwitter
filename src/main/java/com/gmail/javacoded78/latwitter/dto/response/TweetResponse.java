package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TweetResponse {

    private Long id;
    private String text;
    private String addressedUsername;
    private Long addressedId;
    private LocalDateTime dateTime;
    private List<ImageResponse> images;
    private List<UserResponse> retweets;
    private List<UserResponse> likes;
    private List<TweetReplyResponse> replies;
    private UserResponse user;
}
