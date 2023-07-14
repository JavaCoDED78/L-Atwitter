package com.gmail.javacoded78.latwitter.dto.response.tweet;

import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import com.gmail.javacoded78.latwitter.dto.response.QuoteTweetResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.dto.response.poll.PollResponse;
import com.gmail.javacoded78.latwitter.model.LinkCoverSize;
import com.gmail.javacoded78.latwitter.model.ReplyType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TweetResponse {

    private Long id;
    private String text;
    private LocalDateTime dateTime;
    private LocalDateTime scheduledDate;
    private String addressedUsername;
    private Long addressedId;
    private Long addressedTweetId;
    private ReplyType replyType;
    private String link;
    private String linkTitle;
    private String linkDescription;
    private String linkCover;
    private LinkCoverSize linkCoverSize;
    private QuoteTweetResponse quoteTweet;
    private UserResponse user;
    private PollResponse poll;
    private List<ImageResponse> images;
    private List<LikeTweetResponse> likedTweets;
    private List<RetweetResponse> retweets;
    private List<TweetReplyResponse> replies;
    private boolean isTweetDeleted;
}
