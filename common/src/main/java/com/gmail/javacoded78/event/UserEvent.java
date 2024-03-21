package com.gmail.javacoded78.event;

public interface UserEvent {

    Long getId();

    String getFullName();
    String getUsername();
    boolean isPrivateProfile();
}