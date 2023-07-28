package com.gmail.javacoded78.common.projection;

import java.time.LocalDateTime;

public interface LikeTweetProjection {

    Long getId();
    LocalDateTime getLikeTweetDate();
    TweetProjection getTweet();
}
