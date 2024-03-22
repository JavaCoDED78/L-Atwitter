package com.gmail.javacoded78.service;

import com.gmail.javacoded78.event.BlockUserEvent;
import com.gmail.javacoded78.event.FollowUserEvent;
import com.gmail.javacoded78.event.UpdateUserEvent;
import com.gmail.javacoded78.event.UserEvent;
import com.gmail.javacoded78.model.User;

public interface UserService {

    User getAuthUser();

    void validateUserProfile(Long userId);

    void handleUpdateUser(UpdateUserEvent updateUserEvent);

    void handleBlockUser(BlockUserEvent blockUserEvent, String authId);

    void handleFollowUser(FollowUserEvent followUserEvent, String authId);
}
