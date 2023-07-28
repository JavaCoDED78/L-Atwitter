package com.gmail.javacoded78.repository.projection;


import com.gmail.javacoded78.common.projection.ImageProjection;

public interface ListOwnerProjection {

    Long getId();
    String getFullName();
    String getUsername();
    ImageProjection getAvatar();
}
