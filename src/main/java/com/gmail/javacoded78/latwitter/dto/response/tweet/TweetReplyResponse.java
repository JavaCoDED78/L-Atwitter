package com.gmail.javacoded78.latwitter.dto.response.tweet;

import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.model.ReplyType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TweetReplyResponse {

    private Long id;
    private String text;
    private String addressedUsername;
    private Long addressedId;
    private ReplyType replyType;
    private LocalDateTime dateTime;
    private String link;
    private String linkTitle;
    private String linkDescription;
    private String linkCover;
    private List<ImageResponse> images;
    private List<UserResponse> retweets;
    private List<UserResponse> likedTweets;
    private UserResponse user;
}