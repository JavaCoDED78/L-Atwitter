package com.gmail.javacoded78.repository.projection.lists;

import com.gmail.javacoded78.repository.projection.ImageProjection;

public interface ListOwnerProjection {

    Long getId();
    String getFullName();
    String getUsername();
    ImageProjection getAvatar();
}