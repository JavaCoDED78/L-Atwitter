package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.response.FollowerUserResponse;
import com.gmail.javacoded78.dto.response.UserProfileResponse;
import com.gmail.javacoded78.mapper.FollowerUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.UI_V1_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_USER)
public class FollowerUserController {

    private final FollowerUserMapper followerUserMapper;

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<UserResponse>> getFollowers(@PathVariable Long userId, @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = followerUserMapper.getFollowers(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserResponse>> getFollowing(@PathVariable Long userId, @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = followerUserMapper.getFollowing(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/follower-requests")
    public ResponseEntity<List<FollowerUserResponse>> getFollowerRequests(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<FollowerUserResponse> response = followerUserMapper.getFollowerRequests(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/follow/{userId}")
    public ResponseEntity<Boolean> processFollow(@PathVariable Long userId) {
        return ResponseEntity.ok(followerUserMapper.processFollow(userId));
    }

    @GetMapping("/follow/overall/{userId}") // TODO add pagination
    public ResponseEntity<List<UserResponse>> overallFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(followerUserMapper.overallFollowers(userId));
    }

    @GetMapping("/follow/private/{userId}")
    public ResponseEntity<UserProfileResponse> processFollowRequestToPrivateProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(followerUserMapper.processFollowRequestToPrivateProfile(userId));
    }

    @GetMapping("/follow/accept/{userId}")
    public ResponseEntity<String> acceptFollowRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(followerUserMapper.acceptFollowRequest(userId));
    }

    @GetMapping("/follow/decline/{userId}")
    public ResponseEntity<String> declineFollowRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(followerUserMapper.declineFollowRequest(userId));
    }
}
