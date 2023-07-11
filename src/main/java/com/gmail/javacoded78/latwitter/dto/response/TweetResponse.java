package com.gmail.javacoded78.latwitter.dto.response;

import com.gmail.javacoded78.latwitter.model.ReplyType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TweetResponse {

    private Long id;
    private String text;
    private LocalDateTime dateTime;
    private String addressedUsername;
    private Long addressedId;
    private Long addressedTweetId;
    private ReplyType replyType;
    private QuoteTweetResponse quoteTweet;
    private UserResponse user;
    private PollResponse poll;
    private List<ImageResponse> images;
    private List<LikeTweetResponse> likedTweets;
    private List<RetweetResponse> retweets;
    private List<TweetReplyResponse> replies;
    private boolean isTweetDeleted;
}
