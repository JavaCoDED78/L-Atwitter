package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.dto.response.lists.CommonUserResponse;
import org.springframework.beans.factory.annotation.Value;

public interface ListUserProjection {
    Long getId();
    String getName();
    String getDescription();
    String getAltWallpaper();
    String getWallpaper();
    Long getListOwnerId();
    boolean getIsPrivate();

    @Value("#{@listsServiceHelper.getListOwnerById(target.listOwnerId)}")
    CommonUserResponse getListOwner();

    @Value("#{@listsServiceHelper.isListPinned(target.id)}")
    boolean getIsListPinned();
}
