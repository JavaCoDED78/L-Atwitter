package com.gmail.javacoded78.repository.projection.user;


import com.gmail.javacoded78.repository.projection.ImageProjection;

public interface FollowerUserProjection {

    Long getId();

    String getFullName();

    String getUsername();

    String getAbout();

    ImageProjection getAvatar();
}
