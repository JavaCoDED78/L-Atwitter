package com.gmail.javacoded78.latwitter.repository.projection;

import java.time.LocalDateTime;

public interface BookmarkProjection {

    Long getId();

    LocalDateTime getLikeTweetDate();

    TweetProjection getTweet();
}
