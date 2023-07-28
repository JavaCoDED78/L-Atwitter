package com.gmail.javacoded78.controller.api;


import com.gmail.javacoded78.client.user.UserIdsRequest;
import com.gmail.javacoded78.models.User;
import com.gmail.javacoded78.projection.UserChatProjection;
import com.gmail.javacoded78.service.UserClientService;
import com.gmail.javacoded78.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserApiController {

    private final UserClientService userService;

    @GetMapping("/{userId}")
    public Optional<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/ids")
    public List<User> getUsersByIds(@RequestBody UserIdsRequest request) {
        return userService.getUsersByIds(request);
    }

    @GetMapping("/ids")
    public List<Long> getUserFollowersIds() {
        return userService.getUserFollowersIds();
    }

    @GetMapping("/search/{username}")
    public Page<UserChatProjection> searchUsersByUsername(@PathVariable String username, Pageable pageable) {
        return userService.searchUsersByUsername(username, pageable);
    }

    @GetMapping("/valid/{userId}/{authUserId}")
    public Optional<User> getValidUser(@PathVariable Long userId, @PathVariable Long authUserId) {
        return userService.getValidUser(userId, authUserId);
    }

    @GetMapping("/is_blocked/{userId}/{supposedBlockedUserId}")
    public Boolean isUserBlocked(@PathVariable Long userId, @PathVariable Long supposedBlockedUserId) {
        return userService.isUserBlocked(userId, supposedBlockedUserId);
    }

    @GetMapping("/is_user_blocked/{userId}")
    public Boolean isUserBlockedByMyProfile(@PathVariable Long userId) {
        return userService.isUserBlockedByMyProfile(userId);
    }

    @GetMapping("/is_my_profile_blocked/{userId}")
    public Boolean isMyProfileBlockedByUser(@PathVariable Long userId) {
        return userService.isMyProfileBlockedByUser(userId);
    }

    @PostMapping
    public void saveUser(User user) {
        userService.saveUser(user);
    }
}
