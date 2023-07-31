package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.common.projection.ImageProjection;

public interface ListUserProjection {

    Long getId();
    String getName();
    String getDescription();
    String getAltWallpaper();
    ImageProjection getWallpaper();
    ListOwnerProjection getListOwner();
    boolean getIsPrivate();
}
