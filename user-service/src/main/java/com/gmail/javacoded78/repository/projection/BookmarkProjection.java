package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.common.projection.TweetProjection;

import java.time.LocalDateTime;

public interface BookmarkProjection {

    Long getId();
    LocalDateTime getBookmarkDate();
    TweetProjection getTweet();
}
