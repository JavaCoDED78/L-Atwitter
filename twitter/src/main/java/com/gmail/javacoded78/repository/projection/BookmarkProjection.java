package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.repository.projection.tweet.TweetProjection;

import java.time.LocalDateTime;

public interface BookmarkProjection {

    Long getId();
    LocalDateTime getBookmarkDate();
    TweetProjection getTweet();
}
