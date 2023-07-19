package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.request.UserRequest;
import com.gmail.javacoded78.latwitter.dto.response.*;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationsResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetHeaderResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserMapper userMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.getUserById(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userMapper.getUsers());
    }

    @GetMapping("/relevant")
    public ResponseEntity<List<UserResponse>> getRelevantUsers() {
        return ResponseEntity.ok(userMapper.getRelevantUsers());
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<UserResponse>> searchUsersByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userMapper.searchUsersByUsername(username));
    }

    @GetMapping("/start")
    public ResponseEntity<Boolean> startUseTwitter() {
        return ResponseEntity.ok(userMapper.startUseTwitter());
    }

    @GetMapping("/{userId}/tweets")
    public ResponseEntity<List<TweetResponse>> getUserTweets(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
        TweetHeaderResponse response = userMapper.getUserTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getTweets());
    }

    @GetMapping("/{userId}/liked")
    public ResponseEntity<List<TweetResponse>> getUserLikedTweets(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
        TweetHeaderResponse response = userMapper.getUserLikedTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getTweets());
    }

    @GetMapping("/{userId}/media")
    public ResponseEntity<List<TweetResponse>> getUserMediaTweets(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
        TweetHeaderResponse response = userMapper.getUserMediaTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getTweets());
    }

    @GetMapping("/{userId}/replies")
    public ResponseEntity<List<TweetResponse>> getUserRetweetsAndReplies(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
        TweetHeaderResponse response = userMapper.getUserRetweetsAndReplies(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getTweets());
    }

    @GetMapping("/notifications")
    public ResponseEntity<NotificationsResponse> getUserNotifications() {
        return ResponseEntity.ok(userMapper.getUserNotifications());
    }

    @GetMapping("/notifications/timeline")
    public ResponseEntity<List<TweetResponse>> getNotificationsFromTweetAuthors(@PageableDefault(size = 10) Pageable pageable) {
        TweetHeaderResponse response = userMapper.getNotificationsFromTweetAuthors(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getTweets());
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<List<TweetResponse>> getUserBookmarks(@PageableDefault(size = 10) Pageable pageable) {
        TweetHeaderResponse response = userMapper.getUserBookmarks(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getTweets());
    }

    @GetMapping("/bookmarks/{tweetId}")
    public ResponseEntity<Boolean> processUserBookmarks(@PathVariable Long tweetId) {
        return ResponseEntity.ok(userMapper.processUserBookmarks(tweetId));
    }

    @PutMapping
    public ResponseEntity<AuthUserResponse> updateUserProfile(@RequestBody UserRequest userRequest) { // +check
        return ResponseEntity.ok(userMapper.updateUserProfile(userRequest));
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> uploadImage(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(userMapper.uploadImage(file));
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<BaseUserResponse>> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.getFollowers(userId));
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<BaseUserResponse>> getFollowing(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.getFollowing(userId));
    }

    @GetMapping("/follower-requests") // TODO add tests
    public ResponseEntity<List<FollowerUserResponse>> getFollowerRequests() {
        return ResponseEntity.ok(userMapper.getFollowerRequests());
    }

    @GetMapping("/follow/{userId}") //+
    public ResponseEntity<NotificationUserResponse> processFollow(@PathVariable Long userId) {
        NotificationResponse notification = userMapper.processFollow(userId);

        if (notification.getId() != null) {
            messagingTemplate.convertAndSend("/topic/notifications/" + notification.getUserToFollow().getId(), notification);
        }
        return ResponseEntity.ok(notification.getUserToFollow());
    }

    @GetMapping("/follow/overall/{userId}")
    public ResponseEntity<List<BaseUserResponse>> overallFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.overallFollowers(userId));
    }

    @GetMapping("/follow/private/{userId}")
    public ResponseEntity<UserProfileResponse> processFollowRequestToPrivateProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.processFollowRequestToPrivateProfile(userId));
    }

    @GetMapping("/follow/accept/{userId}") //+
    public ResponseEntity<String> acceptFollowRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.acceptFollowRequest(userId));
    }

    @GetMapping("/follow/decline/{userId}")
    public ResponseEntity<String> declineFollowRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.declineFollowRequest(userId));
    }

    @GetMapping("/subscribe/{userId}")
    public ResponseEntity<Boolean> processSubscribeToNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.processSubscribeToNotifications(userId));
    }

    @GetMapping("/pin/tweet/{tweetId}")
    public ResponseEntity<Long> processPinTweet(@PathVariable Long tweetId) {
        return ResponseEntity.ok(userMapper.processPinTweet(tweetId));
    }

    @GetMapping("/blocked")
    public ResponseEntity<List<BlockedUserResponse>> getBlockList() {
        return ResponseEntity.ok(userMapper.getBlockList());
    }

    @GetMapping("/blocked/{userId}")
    public ResponseEntity<Boolean> processBlockList(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.processBlockList(userId));
    }

    @GetMapping("/muted")
    public ResponseEntity<List<MutedUserResponse>> getMutedList() {
        return ResponseEntity.ok(userMapper.getMutedList());
    }

    @GetMapping("/muted/{userId}")
    public ResponseEntity<Boolean> processMutedList(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.processMutedList(userId));
    }

    @GetMapping("/details/{userId}") // TODO Add tests
    public ResponseEntity<UserDetailResponse> getUserDetails(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.getUserDetails(userId));
    }
}
