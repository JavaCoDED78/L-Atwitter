package com.gmail.javacoded78.latwitter.repository.projection.lists;

import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;

public interface ListMemberProjection {

    Long getId();

    String getFullName();

    String getUsername();

    String getAbout();

    boolean isPrivateProfile();

    ImageProjection getAvatar();
}
