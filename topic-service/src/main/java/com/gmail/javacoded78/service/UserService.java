package com.gmail.javacoded78.service;

import com.gmail.javacoded78.event.UserEvent;

public interface UserService {

    boolean isUserExists(Long userId);

    boolean isMyProfileBlockedByUser(Long userId);

    boolean isUserHavePrivateProfile(Long userId);

    void handleUser(UserEvent userEvent);
}
