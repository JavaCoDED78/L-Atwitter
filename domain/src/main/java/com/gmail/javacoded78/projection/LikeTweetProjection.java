package com.gmail.javacoded78.projection;

import com.gmail.javacoded78.repository.projection.tweet.TweetProjection;

import java.time.LocalDateTime;

public interface LikeTweetProjection {

    Long getId();
    LocalDateTime getLikeTweetDate();
    TweetProjection getTweet();
}
