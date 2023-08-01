package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

public interface ChatUserParticipantProjection {

    Long getId();
    String getFullName();
    String getUsername();
    ImageProjection getAvatar();
    boolean isMutedDirectMessages();

    @Value("#{@userServiceImpl.isUserBlockedByMyProfile(target.id)}")
    boolean getIsUserBlocked();

    @Value("#{@userServiceImpl.isMyProfileBlockedByUser(target.id)}")
    boolean getIsMyProfileBlocked();
}
