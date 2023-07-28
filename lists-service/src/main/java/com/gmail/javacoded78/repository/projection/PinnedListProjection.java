package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.common.projection.ImageProjection;

import java.time.LocalDateTime;

public interface PinnedListProjection {

    Long getId();
    String getName();
    LocalDateTime getPinnedDate();
    String getAltWallpaper();
    ImageProjection getWallpaper();
    boolean getIsPrivate();
}
