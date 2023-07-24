package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.request.UserRequest;
import com.gmail.javacoded78.latwitter.dto.response.*;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationInfoResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetImageResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetUserResponse;
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
    public ResponseEntity<List<UserResponse>> getUsers(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = userMapper.getUsers(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/relevant")
    public ResponseEntity<List<UserResponse>> getRelevantUsers() {
        return ResponseEntity.ok(userMapper.getRelevantUsers());
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<UserResponse>> searchUsersByUsername(@PathVariable String username, @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = userMapper.searchUsersByUsername(username, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/start")
    public ResponseEntity<Boolean> startUseTwitter() {
        return ResponseEntity.ok(userMapper.startUseTwitter());
    }

    @GetMapping("/{userId}/tweets")
    public ResponseEntity<List<TweetUserResponse>> getUserTweets(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetUserResponse> response = userMapper.getUserTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/{userId}/liked")
    public ResponseEntity<List<TweetResponse>> getUserLikedTweets(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = userMapper.getUserLikedTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/{userId}/media")
    public ResponseEntity<List<TweetResponse>> getUserMediaTweets(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = userMapper.getUserMediaTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/{userId}/replies")
    public ResponseEntity<List<TweetUserResponse>> getUserRetweetsAndReplies(@PathVariable Long userId, @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetUserResponse> response = userMapper.getUserRetweetsAndReplies(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<NotificationResponse> response = userMapper.getUserNotifications(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/notifications/subscribes")
    public ResponseEntity<List<NotificationUserResponse>> getTweetAuthorsNotifications() {
        return ResponseEntity.ok(userMapper.getTweetAuthorsNotifications());
    }

    @GetMapping("/notifications/{notificationId}")
    public ResponseEntity<NotificationInfoResponse> getUserNotificationById(@PathVariable Long notificationId) {
        return ResponseEntity.ok(userMapper.getUserNotificationById(notificationId));
    }

    @GetMapping("/notifications/timeline")
    public ResponseEntity<List<TweetResponse>> getNotificationsFromTweetAuthors(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = userMapper.getNotificationsFromTweetAuthors(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/mentions")
    public ResponseEntity<List<TweetResponse>> getUserMentions(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = userMapper.getUserMentions(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<List<TweetResponse>> getUserBookmarks(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = userMapper.getUserBookmarks(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/bookmarks/{tweetId}")
    public ResponseEntity<Boolean> processUserBookmarks(@PathVariable Long tweetId) {
        return ResponseEntity.ok(userMapper.processUserBookmarks(tweetId));
    }

    @PutMapping
    public ResponseEntity<AuthUserResponse> updateUserProfile(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userMapper.updateUserProfile(userRequest));
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> uploadImage(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(userMapper.uploadImage(file));
    }

    @GetMapping("/images/{userId}")
    public ResponseEntity<List<TweetImageResponse>> getUserTweetImages(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.getUserTweetImages(userId));
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<UserResponse>> getFollowers(@PathVariable Long userId, @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = userMapper.getFollowers(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserResponse>> getFollowing(@PathVariable Long userId, @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = userMapper.getFollowing(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/follower-requests")
    public ResponseEntity<List<FollowerUserResponse>> getFollowerRequests(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<FollowerUserResponse> response = userMapper.getFollowerRequests(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/follow/{userId}")
    public ResponseEntity<NotificationUserResponse> processFollow(@PathVariable Long userId) {
        NotificationResponse notification = userMapper.processFollow(userId);

        if (notification.getId() != null) {
            messagingTemplate.convertAndSend("/topic/notifications/" + notification.getUserToFollow().getId(), notification);
        }
        return ResponseEntity.ok(notification.getUserToFollow());
    }

    @GetMapping("/follow/overall/{userId}") // TODO add pagination
    public ResponseEntity<List<UserResponse>> overallFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.overallFollowers(userId));
    }

    @GetMapping("/follow/private/{userId}")
    public ResponseEntity<UserProfileResponse> processFollowRequestToPrivateProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.processFollowRequestToPrivateProfile(userId));
    }

    @GetMapping("/follow/accept/{userId}")
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
    public ResponseEntity<List<BlockedUserResponse>> getBlockList(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<BlockedUserResponse> response = userMapper.getBlockList(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/blocked/{userId}")
    public ResponseEntity<Boolean> processBlockList(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.processBlockList(userId));
    }

    @GetMapping("/muted")
    public ResponseEntity<List<MutedUserResponse>> getMutedList(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<MutedUserResponse> response = userMapper.getMutedList(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/muted/{userId}")
    public ResponseEntity<Boolean> processMutedList(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.processMutedList(userId));
    }

    @GetMapping("/details/{userId}")
    public ResponseEntity<UserDetailResponse> getUserDetails(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.getUserDetails(userId));
    }
}
