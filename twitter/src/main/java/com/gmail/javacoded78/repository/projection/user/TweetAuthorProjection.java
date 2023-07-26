package com.gmail.javacoded78.repository.projection.user;


import com.gmail.javacoded78.repository.projection.ImageProjection;

public interface TweetAuthorProjection {

    AuthorProjection getTweetAuthor();

    interface AuthorProjection {

        Long getId();

        String getUsername();

        String getFullName();

        ImageProjection getAvatar();
    }
}
