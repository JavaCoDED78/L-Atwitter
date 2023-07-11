package com.gmail.javacoded78.latwitter.dto.response.notification;

import com.gmail.javacoded78.latwitter.dto.response.*;
import com.gmail.javacoded78.latwitter.dto.response.tweet.LikeTweetResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.RetweetResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetReplyResponse;
import com.gmail.javacoded78.latwitter.model.ReplyType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NotificationTweetResponse {

    private Long id;
    private String text;
    private LocalDateTime dateTime;
    private String addressedUsername;
    private Long addressedId;
    private ReplyType replyType;
    private NotificationUserResponse user;
    private List<ImageResponse> images;
    private List<LikeTweetResponse> likedTweets;
    private List<RetweetResponse> retweets;
    private List<TweetReplyResponse> replies;
}
