package com.gmail.javacoded78.latwitter.repository.projection.user;

import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;

public interface FollowerUserProjection {

    Long getId();

    String getFullName();

    String getUsername();

    String getAbout();

    ImageProjection getAvatar();
}
