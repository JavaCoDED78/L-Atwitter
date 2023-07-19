package com.gmail.javacoded78.latwitter.repository.projection.user;

import com.gmail.javacoded78.latwitter.repository.projection.TweetProjection;

public interface UserListProjection {

    TweetProjection.UserProjection getUser();
}
