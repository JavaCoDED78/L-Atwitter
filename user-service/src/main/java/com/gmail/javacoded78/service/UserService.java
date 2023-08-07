package com.gmail.javacoded78.service;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.projection.AuthUserProjection;
import com.gmail.javacoded78.repository.projection.BaseUserProjection;
import com.gmail.javacoded78.repository.projection.BlockedUserProjection;
import com.gmail.javacoded78.repository.projection.FollowerUserProjection;
import com.gmail.javacoded78.repository.projection.MutedUserProjection;
import com.gmail.javacoded78.repository.projection.UserDetailProjection;
import com.gmail.javacoded78.repository.projection.UserProfileProjection;
import com.gmail.javacoded78.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserProfileProjection getUserById(Long userId);

    Page<UserProjection> getUsers(Pageable pageable);

    List<UserProjection> getRelevantUsers();

    <T> Page<T> searchUsersByUsername(String username, Pageable pageable, Class<T> type);

    Map<String, Object> searchByText(String text);

    Boolean startUseTwitter();

    AuthUserProjection updateUserProfile(User userInfo);

    Boolean processSubscribeToNotifications(Long userId);

    Long processPinTweet(Long tweetId);

    UserDetailProjection getUserDetails(Long userId);
}
