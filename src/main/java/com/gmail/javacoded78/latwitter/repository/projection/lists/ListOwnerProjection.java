package com.gmail.javacoded78.latwitter.repository.projection.lists;

import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;

public interface ListOwnerProjection {

    Long getId();

    String getFullName();

    String getUsername();

    ImageProjection getAvatar();
}
