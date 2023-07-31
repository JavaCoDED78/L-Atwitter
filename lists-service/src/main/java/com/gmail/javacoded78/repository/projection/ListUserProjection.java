package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.projection.ListOwnerProjection;
import org.springframework.beans.factory.annotation.Value;

public interface ListUserProjection {
    Long getId();
    String getName();
    String getDescription();
    String getAltWallpaper();
    ListsWallpaperProjection getWallpaper();
    boolean getIsPrivate();
    Long getListOwnerId();

    @Value("#{@listsServiceImpl.getListOwnerById(target.listOwnerId)}")
    ListOwnerProjection getListOwner();
}
