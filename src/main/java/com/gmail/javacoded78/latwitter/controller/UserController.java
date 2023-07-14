package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.request.UserRequest;
import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
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
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
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

    @GetMapping("/{userId}/start")
    public ResponseEntity<UserResponse> startUseTwitter(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.startUseTwitter(userId));
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
    public ResponseEntity<List<NotificationResponse>> getUserNotifications() {
        return ResponseEntity.ok(userMapper.getUserNotifications());
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<List<TweetResponse>> getUserBookmarks(@PageableDefault(size = 10) Pageable pageable) {
        TweetHeaderResponse response = userMapper.getUserBookmarks(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getTweets());
    }

    @GetMapping("/bookmarks/{tweetId}")
    public ResponseEntity<UserResponse> processUserBookmarks(@PathVariable Long tweetId) {
        return ResponseEntity.ok(userMapper.processUserBookmarks(tweetId));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUserProfile(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userMapper.updateUserProfile(userRequest));
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> uploadImage(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(userMapper.uploadImage(file));
    }

    @GetMapping("/follow/{userId}")
    public ResponseEntity<NotificationUserResponse> processFollow(@PathVariable Long userId) {
        NotificationResponse notification = userMapper.processFollow(userId);

        if (notification.getId() != null) {
            messagingTemplate.convertAndSend("/topic/notifications/" + notification.getUserToFollow().getId(), notification);
        }
        return ResponseEntity.ok(notification.getUserToFollow());
    }

    @GetMapping("/pin/tweet/{tweetId}")
    public ResponseEntity<UserResponse> processPinTweet(@PathVariable Long tweetId) {
        return ResponseEntity.ok(userMapper.processPinTweet(tweetId));
    }
}
