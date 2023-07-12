package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.request.ListsRequest;
import com.gmail.javacoded78.latwitter.dto.request.TweetToListsRequest;
import com.gmail.javacoded78.latwitter.dto.request.UserToListsRequest;
import com.gmail.javacoded78.latwitter.dto.response.ListsResponse;
import com.gmail.javacoded78.latwitter.mapper.ListsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lists")
public class ListsController {

    private final ListsMapper listsMapper;

    @GetMapping
    public ResponseEntity<List<ListsResponse>> getAllTweetLists() {
        return ResponseEntity.ok(listsMapper.getAllTweetLists());
    }

    @GetMapping("/user")
    public ResponseEntity<List<ListsResponse>> getUserTweetLists() {
        return ResponseEntity.ok(listsMapper.getUserTweetLists());
    }

    @GetMapping("/pined")
    public ResponseEntity<List<ListsResponse>> getUserPinnedLists() {
        return ResponseEntity.ok(listsMapper.getUserPinnedLists());
    }

    @GetMapping("/{listId}")
    public ResponseEntity<ListsResponse> getListById(@PathVariable Long listId) {
        return ResponseEntity.ok(listsMapper.getListById(listId));
    }

    @PostMapping
    public ResponseEntity<ListsResponse> createTweetList(@RequestBody ListsRequest listsRequest) {
        return ResponseEntity.ok(listsMapper.createTweetList(listsRequest));
    }

    @GetMapping("/follow/{listId}")
    public ResponseEntity<ListsResponse> followList(@PathVariable Long listId) {
        return ResponseEntity.ok(listsMapper.followList(listId));
    }

    @GetMapping("/pin/{listId}")
    public ResponseEntity<ListsResponse> pinList(@PathVariable Long listId) {
        return ResponseEntity.ok(listsMapper.pinList(listId));
    }

    @PostMapping("/add/tweet")
    public ResponseEntity<List<ListsResponse>> addTweetToLists(@RequestBody TweetToListsRequest tweetToListsRequest) {
        return ResponseEntity.ok(listsMapper.addTweetToLists(tweetToListsRequest.getTweetId(), tweetToListsRequest.getLists()));
    }

    @PostMapping("/add/user")
    public ResponseEntity<List<ListsResponse>> addUserToLists(@RequestBody UserToListsRequest userToListsRequest) {
        return ResponseEntity.ok(listsMapper.addUserToLists(userToListsRequest.getUserId(), userToListsRequest.getLists()));
    }

    @GetMapping("/add/user/{userId}/{listId}")
    public ResponseEntity<ListsResponse> addUserToList(@PathVariable Long userId, @PathVariable Long listId) {
        return ResponseEntity.ok(listsMapper.addUserToList(userId, listId));
    }
}