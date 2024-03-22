package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import org.springframework.beans.factory.annotation.Value;

public interface TweetListProjection {

    Long getId();
    String getListName();
    String getAltWallpaper();
    String getWallpaper();
    Long getListOwnerId();
    boolean getIsPrivate();

    @Value("#{@listsServiceHelper.getListOwnerById(target.listOwnerId)}")
    CommonUserResponse getListOwner();

    @Value("#{@listsMembersRepository.getMembersSize(target.id)}")
    Long getMembersSize();
}
