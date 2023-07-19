package com.gmail.javacoded78.latwitter.repository.projection.user;

import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

public interface MutedUserProjection {

    Long getId();

    String getFullName();

    String getUsername();

    String getAbout();

    ImageProjection getAvatar();

    boolean getIsPrivateProfile();

    @Value("#{@userServiceImpl.isUserMutedByMyProfile(target.id)}")
    boolean getIsUserMuted();
}
