package com.gmail.javacoded78.latwitter.repository.projection.user;

import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;

public interface TweetAuthorProjection {

    AuthorProjection getTweetAuthor();

    interface AuthorProjection {

        Long getId();

        String getUsername();

        String getFullName();

        ImageProjection getAvatar();
    }
}
