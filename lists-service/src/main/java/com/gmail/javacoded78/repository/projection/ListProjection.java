package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.dto.response.lists.ListOwnerResponse;
import org.springframework.beans.factory.annotation.Value;

public interface ListProjection {
    Long getId();
    String getName();
    String getDescription();
    String getAltWallpaper();
    String getWallpaper();
    Long getListOwnerId();

    @Value("#{@listsServiceImpl.getListOwnerById(target.listOwnerId)}")
    ListOwnerResponse getListOwner();

    @Value("#{@listsServiceImpl.isMyProfileFollowList(target.id)}")
    boolean getIsFollower();

    @Value("#{@listsServiceImpl.isListPinned(target.id)}")
    boolean getIsListPinned();
}
