package com.gmail.javacoded78.repository.projection.lists;


import com.gmail.javacoded78.repository.projection.ImageProjection;

public interface ListMemberProjection {

    Long getId();

    String getFullName();

    String getUsername();

    String getAbout();

    ImageProjection getAvatar();

    boolean getIsPrivateProfile();
}
