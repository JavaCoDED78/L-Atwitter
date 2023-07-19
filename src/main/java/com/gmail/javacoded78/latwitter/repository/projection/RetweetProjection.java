package com.gmail.javacoded78.latwitter.repository.projection;

import java.time.LocalDateTime;

public interface RetweetProjection {

    Long getId();

    LocalDateTime getRetweetDate();

    TweetProjection getTweet();
}