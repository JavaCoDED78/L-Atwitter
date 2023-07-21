package com.gmail.javacoded78.latwitter.repository.projection.tweet;

import java.time.LocalDateTime;

public interface RetweetProjection {

    Long getId();

    LocalDateTime getRetweetDate();

    TweetUserProjection getTweet();
}