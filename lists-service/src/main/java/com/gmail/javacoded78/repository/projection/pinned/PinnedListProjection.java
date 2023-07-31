package com.gmail.javacoded78.repository.projection.pinned;

import com.gmail.javacoded78.common.projection.ImageProjection;

public interface PinnedListProjection {

    Long getId();
    String getName();
    String getAltWallpaper();
    ImageProjection getWallpaper();
    boolean getIsPrivate();
}
