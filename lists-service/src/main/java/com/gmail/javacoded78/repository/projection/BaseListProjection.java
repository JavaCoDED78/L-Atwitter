package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.projection.ListOwnerProjection;
import org.springframework.beans.factory.annotation.Value;

public interface BaseListProjection {
    Long getId();
    String getName();
    String getDescription();
    String getAltWallpaper();
    String getWallpaper();
    Long getListOwnerId();
    boolean getIsPrivate();

    @Value("#{@listsServiceImpl.getListOwnerById(target.listOwnerId)}")
    ListOwnerProjection getListOwner();

    @Value("#{@listsMembersRepository.getMembersSize(target.id)}")
    Long getMembersSize();

    @Value("#{@listsFollowersRepository.getFollowersSize(target.id)}")
    Long getFollowersSize();

    @Value("#{@listsServiceImpl.isMyProfileFollowList(target.id)}")
    boolean getIsFollower();
}
