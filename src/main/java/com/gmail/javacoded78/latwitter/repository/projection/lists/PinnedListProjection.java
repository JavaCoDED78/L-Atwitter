package com.gmail.javacoded78.latwitter.repository.projection.lists;

import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;

public interface PinnedListProjection {

    Long getId();

    String getName();

    String getAltWallpaper();

    ImageProjection getWallpaper();
}
