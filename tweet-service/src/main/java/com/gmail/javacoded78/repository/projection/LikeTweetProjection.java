package com.gmail.javacoded78.repository.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface LikeTweetProjection {

    Long getId();
    LocalDateTime getLikeTweetDate();
    Long getTweetId();

    @Value("#{@tweetServiceHelper.getTweetProjection(target.tweetId)}")
    TweetProjection getTweet();
}
