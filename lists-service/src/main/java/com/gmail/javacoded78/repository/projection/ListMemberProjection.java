package com.gmail.javacoded78.repository.projection;

public interface ListMemberProjection {
    Long getId();
    String getFullName();
    String getUsername();
    String getAbout();
    ListsWallpaperProjection getAvatar();
    boolean getIsPrivateProfile();
}