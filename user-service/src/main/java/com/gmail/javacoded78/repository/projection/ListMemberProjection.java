package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.common.projection.ImageProjection;
import org.springframework.beans.factory.annotation.Value;

public interface ListMemberProjection {

    Long getId();
    String getFullName();
    String getUsername();
    String getAbout();
    ImageProjection getAvatar();
    boolean getPrivateProfile();

    @Value("#{false}")
    boolean getMemberInList();
}
