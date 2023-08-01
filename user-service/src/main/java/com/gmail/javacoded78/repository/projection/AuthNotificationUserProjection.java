package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

public interface AuthNotificationUserProjection {

    Long getId();
    String getUsername();
    String getFullName();
    ImageProjection getAvatar();
    @Value("#{false}")
    boolean getIsFollower();
}
