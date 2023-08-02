package com.gmail.javacoded78.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface MutedUserProjection {

    Long getId();
    String getFullName();
    String getUsername();
    String getAbout();
    String getAvatar();
    boolean getIsPrivateProfile();

    @Value("#{@userServiceHelper.isUserMutedByMyProfile(target.id)}")
    boolean getIsUserMuted();
}
