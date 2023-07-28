package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.common.projection.ImageProjection;

public interface SimpleListProjection {

    Long getId();
    String getName();
    String getAltWallpaper();
    ImageProjection getWallpaper();
    boolean getIsPrivate();
}
