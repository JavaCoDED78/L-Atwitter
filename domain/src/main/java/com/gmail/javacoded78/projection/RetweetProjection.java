package com.gmail.javacoded78.projection;

import java.time.LocalDateTime;

public interface RetweetProjection {

    Long getId();
    LocalDateTime getRetweetDate();
    TweetUserProjection getTweet();
}
