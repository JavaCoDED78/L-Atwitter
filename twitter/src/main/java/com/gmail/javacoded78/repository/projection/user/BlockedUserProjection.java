package com.gmail.javacoded78.repository.projection.user;

import com.gmail.javacoded78.repository.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

public interface BlockedUserProjection {

    Long getId();

    String getFullName();

    String getUsername();

    String getAbout();

    ImageProjection getAvatar();

    boolean getIsPrivateProfile();

    @Value("#{@userServiceImpl.isUserBlockedByMyProfile(target.id)}")
    boolean getIsUserBlocked();
}
