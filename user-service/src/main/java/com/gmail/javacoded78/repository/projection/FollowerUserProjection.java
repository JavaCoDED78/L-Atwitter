package com.gmail.javacoded78.repository.projection;


import com.gmail.javacoded78.common.projection.ImageProjection;

public interface FollowerUserProjection {

    Long getId();
    String getFullName();
    String getUsername();
    String getAbout();
    ImageProjection getAvatar();
}
