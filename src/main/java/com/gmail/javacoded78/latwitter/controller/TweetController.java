package com.gmail.javacoded78.latwitter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.javacoded78.latwitter.dto.Views;
import com.gmail.javacoded78.latwitter.dto.request.TweetRequest;
import com.gmail.javacoded78.latwitter.dto.response.TweetResponse;
import com.gmail.javacoded78.latwitter.mapper.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tweets")
public class TweetController {

    private final TweetMapper tweetMapper;

    @GetMapping
    @JsonView(Views.Tweet.class)
    public ResponseEntity<List<TweetResponse>> getTweets() {
        return ResponseEntity.ok(tweetMapper.getTweets());
    }

    @GetMapping("/{tweetId}")
    @JsonView(Views.Tweet.class)
    public ResponseEntity<TweetResponse> getTweetById(@PathVariable Long tweetId) {
        return ResponseEntity.ok(tweetMapper.getTweetById(tweetId));
    }

    @PostMapping
    @JsonView(Views.Tweet.class)
    public ResponseEntity<List<TweetResponse>> createTweet(@RequestBody TweetRequest tweetRequest) {
        return ResponseEntity.ok(tweetMapper.createTweet(tweetRequest));
    }

    @DeleteMapping("/{tweetId}")
    @JsonView(Views.Tweet.class)
    public ResponseEntity<List<TweetResponse>> deleteTweet(@PathVariable Long tweetId) {
        return ResponseEntity.ok(tweetMapper.deleteTweet(tweetId));
    }

    @GetMapping("/like/{tweetId}")
    @JsonView(Views.Tweet.class)
    public ResponseEntity<TweetResponse> likeTweet(@PathVariable Long tweetId) {
        return ResponseEntity.ok(tweetMapper.likeTweet(tweetId));
    }
}
