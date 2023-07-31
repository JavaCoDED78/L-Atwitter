package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.common.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

public interface BaseListProjection {

    Long getId();
    String getName();
    String getDescription();
    String getAltWallpaper();
    ImageProjection getWallpaper();
    ListOwnerProjection getListOwner();
    boolean getIsPrivate();

    @Value("#{@listsServiceImpl.isMyProfileFollowList(target.id)}")
    boolean getIsFollower();

    @Value("#{target.members.size()}")
    Integer getMembersSize();

    @Value("#{target.followers.size()}")
    Integer getFollowersSize();
}
