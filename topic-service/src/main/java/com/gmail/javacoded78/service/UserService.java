package com.gmail.javacoded78.service;

import com.gmail.javacoded78.event.BlockUserEvent;
import com.gmail.javacoded78.event.FollowUserEvent;
import com.gmail.javacoded78.event.UpdateUserEvent;
import com.gmail.javacoded78.event.UserEvent;

public interface UserService {

    boolean isUserExists(Long userId);

    boolean isMyProfileBlockedByUser(Long userId);

    boolean isUserHavePrivateProfile(Long userId);

    void handleUpdateUser(UpdateUserEvent updateUserEvent);

    void handleBlockUser(BlockUserEvent blockUserEvent, String authId);

    void handleFollowUser(FollowUserEvent followUserEvent, String authId);
}
