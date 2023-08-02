package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.dto.response.lists.ListOwnerResponse;
import org.springframework.beans.factory.annotation.Value;

public interface ListUserProjection {

    Long getId();
    String getName();
    String getDescription();
    String getAltWallpaper();
    String getWallpaper();
    boolean getIsPrivate();
    Long getListOwnerId();

    @Value("#{@listsServiceImpl.getListOwnerById(target.listOwnerId)}")
    ListOwnerResponse getListOwner();

    @Value("#{@listsServiceImpl.isListPinned(target.id)}")
    boolean getIsListPinned();
}
