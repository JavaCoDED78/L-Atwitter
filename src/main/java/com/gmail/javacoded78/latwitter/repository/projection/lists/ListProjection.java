package com.gmail.javacoded78.latwitter.repository.projection.lists;

import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface ListProjection {

    Long getId();

    String getName();

    String getDescription();

    LocalDateTime getPinnedDate();

    String getAltWallpaper();

    ImageProjection getWallpaper();

    ListOwnerProjection getListOwner();

    @Value("#{@listsServiceImpl.isMyProfileFollowList(target.id)}")
    boolean getIsFollower();
}
