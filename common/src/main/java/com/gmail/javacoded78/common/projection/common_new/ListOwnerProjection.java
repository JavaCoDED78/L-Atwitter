package com.gmail.javacoded78.common.projection.common_new;


import com.gmail.javacoded78.common.projection.ImageProjection;

public interface ListOwnerProjection {

    Long getId();
    String getFullName();
    String getUsername();
    ImageProjection getAvatar();
}
