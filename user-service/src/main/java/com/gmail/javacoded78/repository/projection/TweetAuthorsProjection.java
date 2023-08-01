package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.common.projection.ImageProjection;

public interface TweetAuthorsProjection {
    AuthorProjection getTweetAuthor();

    interface AuthorProjection {

        Long getId();
        String getUsername();
        String getFullName();
        ImageProjection getAvatar();
    }
}
