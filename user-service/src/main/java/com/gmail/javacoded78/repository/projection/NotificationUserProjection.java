package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.common.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

public interface NotificationUserProjection {

    Long getId();
    String getUsername();
    String getFullName();
    ImageProjection getAvatar();

    @Value("#{@userServiceImpl.isUserFollowByOtherUser(target.id)}")
    boolean getIsFollower();
}
