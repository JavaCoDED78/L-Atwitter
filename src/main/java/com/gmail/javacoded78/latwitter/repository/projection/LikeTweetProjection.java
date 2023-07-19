package com.gmail.javacoded78.latwitter.repository.projection;

import java.time.LocalDateTime;

public interface LikeTweetProjection {

    Long getId();

    LocalDateTime getBookmarkDate();

    TweetProjection getTweet();
}
