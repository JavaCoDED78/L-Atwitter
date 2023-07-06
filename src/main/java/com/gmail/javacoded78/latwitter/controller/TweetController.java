package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.request.TweetRequest;
import com.gmail.javacoded78.latwitter.dto.request.UserRequest;
import com.gmail.javacoded78.latwitter.dto.response.TweetResponse;
import com.gmail.javacoded78.latwitter.mapper.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tweets")
public class TweetController {

    private final TweetMapper tweetMapper;

    @GetMapping
    public ResponseEntity<List<TweetResponse>> getTweets() {
        return ResponseEntity.ok(tweetMapper.getTweets());
    }

    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetResponse> getTweetById(@PathVariable Long tweetId) {
        return ResponseEntity.ok(tweetMapper.getTweetById(tweetId));
    }

    @PostMapping("/user")
    public ResponseEntity<List<TweetResponse>> getTweetsByUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(tweetMapper.getTweetsByUser(userRequest));
    }

    @GetMapping("/media")
    public ResponseEntity<List<TweetResponse>> getMediaTweets() {
        return ResponseEntity.ok(tweetMapper.getMediaTweets());
    }

    @PostMapping
    public ResponseEntity<TweetResponse> createTweet(@RequestBody TweetRequest tweetRequest) {
        return ResponseEntity.ok(tweetMapper.createTweet(tweetRequest));
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<List<TweetResponse>> deleteTweet(@PathVariable Long tweetId) {
        return ResponseEntity.ok(tweetMapper.deleteTweet(tweetId));
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<List<TweetResponse>> searchTweets(@PathVariable String text) {
        return ResponseEntity.ok(tweetMapper.searchTweets(text));
    }

    @GetMapping("/like/{tweetId}")
    public ResponseEntity<TweetResponse> likeTweet(@PathVariable Long tweetId) {
        return ResponseEntity.ok(tweetMapper.likeTweet(tweetId));
    }

    @GetMapping("/retweet/{tweetId}")
    public ResponseEntity<TweetResponse> retweet(@PathVariable Long tweetId) {
        return ResponseEntity.ok(tweetMapper.retweet(tweetId));
    }

    @PostMapping("/reply/{tweetId}")
    public ResponseEntity<TweetResponse> replyTweet(@PathVariable Long tweetId, @RequestBody TweetRequest tweetRequest) {
        return ResponseEntity.ok(tweetMapper.replyTweet(tweetId, tweetRequest));
    }
}
