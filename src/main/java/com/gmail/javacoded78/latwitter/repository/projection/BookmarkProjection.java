package com.gmail.javacoded78.latwitter.repository.projection;

import com.gmail.javacoded78.latwitter.repository.projection.tweet.TweetProjection;

import java.time.LocalDateTime;

public interface BookmarkProjection {

    Long getId();

    LocalDateTime getBookmarkDate();

    TweetProjection getTweet();
}
