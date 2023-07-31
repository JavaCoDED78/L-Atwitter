package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.common.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

public interface ListProjection {
    Long getId();
    String getName();
    String getDescription();
    String getAltWallpaper();
    ImageProjection getWallpaper();
    ListOwnerProjection getListOwner();

    @Value("#{@listsServiceImpl.isMyProfileFollowList(target.id)}")
    boolean getIsFollower();
}
