package com.gmail.javacoded78.repository.projection.pinned;


import com.gmail.javacoded78.repository.projection.ListsWallpaperProjection;

public interface PinnedListProjection {

    Long getId();
    String getName();
    String getAltWallpaper();
    ListsWallpaperProjection getWallpaper();
    boolean getIsPrivate();
}
