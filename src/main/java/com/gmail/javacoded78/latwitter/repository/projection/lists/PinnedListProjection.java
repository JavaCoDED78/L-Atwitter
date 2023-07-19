package com.gmail.javacoded78.latwitter.repository.projection.lists;

import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;

import java.time.LocalDateTime;

public interface PinnedListProjection {

    Long getId();

    String getName();

    LocalDateTime getPinnedDate();

    String getAltWallpaper();

    ImageProjection getWallpaper();

    boolean getIsPrivate();
}
