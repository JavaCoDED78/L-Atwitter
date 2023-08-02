package com.gmail.javacoded78.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ListMemberProjection {

    Long getId();
    String getFullName();
    String getUsername();
    String getAbout();
    String getAvatar();
    boolean getPrivateProfile();

    @Value("#{false}")
    boolean getMemberInList();
}
