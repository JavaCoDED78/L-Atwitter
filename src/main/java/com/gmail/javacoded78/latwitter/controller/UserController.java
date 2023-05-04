package com.gmail.javacoded78.latwitter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.javacoded78.latwitter.dto.Views;
import com.gmail.javacoded78.latwitter.dto.request.TweetRequest;
import com.gmail.javacoded78.latwitter.dto.response.TweetResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserMapper userMapper;

    @GetMapping("/{userId}")
    @JsonView(Views.User.class)
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.getUserById(userId));
    }

    @GetMapping("/tweets")
    @JsonView(Views.Tweet.class)
    public ResponseEntity<List<TweetResponse>> getTweets() {
        return ResponseEntity.ok(userMapper.getTweets());
    }

    @GetMapping("/tweet/{tweetId}")
    public ResponseEntity<TweetResponse> getTweetById(@PathVariable Long tweetId) {
        return ResponseEntity.ok(userMapper.getTweetById(tweetId));
    }

    @PostMapping("/create/tweet")
    public ResponseEntity<List<TweetResponse>> createTweet(@RequestBody TweetRequest tweetRequest) {
        return ResponseEntity.ok(userMapper.createTweet(tweetRequest));
    }

    @DeleteMapping("/delete/tweet/{tweetId}")
    public ResponseEntity<List<TweetResponse>> deleteTweet(@PathVariable Long tweetId) {
        return ResponseEntity.ok(userMapper.deleteTweet(tweetId));
    }
}
