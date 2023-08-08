package com.gmail.javacoded78.repository.projection;

public interface CommonUserProjection {

    Long getId();

    String getFullName();

    String getUsername();

    String getAvatar();

    boolean isPrivateProfile();
}