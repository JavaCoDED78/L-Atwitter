package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.request.ListsRequest;
import com.gmail.javacoded78.latwitter.dto.request.UserToListsRequest;
import com.gmail.javacoded78.latwitter.dto.response.HeaderResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.BaseListResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.ListMemberResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.ListResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.ListUserResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.ListsOwnerMemberResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.PinnedListResponse;
import com.gmail.javacoded78.latwitter.dto.response.lists.SimpleListResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.mapper.ListsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lists")
public class ListsController {

    private final ListsMapper listsMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public ResponseEntity<List<ListResponse>> getAllTweetLists() {
        return ResponseEntity.ok(listsMapper.getAllTweetLists());
    }

    @GetMapping("/user")
    public ResponseEntity<List<ListUserResponse>> getUserTweetLists() {
        return ResponseEntity.ok(listsMapper.getUserTweetLists());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ListResponse>> getUserTweetListsById(@PathVariable Long userId) {
        return ResponseEntity.ok(listsMapper.getUserTweetListsById(userId));
    }

    @GetMapping("/user/consist")
    public ResponseEntity<List<ListResponse>> getTweetListsWhichUserIn() {
        return ResponseEntity.ok(listsMapper.getTweetListsWhichUserIn());
    }

    @GetMapping("/pined")
    public ResponseEntity<List<PinnedListResponse>> getUserPinnedLists() {
        return ResponseEntity.ok(listsMapper.getUserPinnedLists());
    }

    @GetMapping("/{listId}")
    public ResponseEntity<BaseListResponse> getListById(@PathVariable Long listId) {
        return ResponseEntity.ok(listsMapper.getListById(listId));
    }

    @PostMapping
    public ResponseEntity<ListUserResponse> createTweetList(@RequestBody ListsRequest listsRequest) {
        return ResponseEntity.ok(listsMapper.createTweetList(listsRequest));
    }

    @PutMapping
    public ResponseEntity<BaseListResponse> editTweetList(@RequestBody ListsRequest listsRequest) {
        return ResponseEntity.ok(listsMapper.editTweetList(listsRequest));
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<String> deleteList(@PathVariable Long listId) {
        return ResponseEntity.ok(listsMapper.deleteList(listId));
    }

    @GetMapping("/follow/{listId}")
    public ResponseEntity<ListUserResponse> followList(@PathVariable Long listId) {
        return ResponseEntity.ok(listsMapper.followList(listId));
    }

    @GetMapping("/pin/{listId}")
    public ResponseEntity<PinnedListResponse> pinList(@PathVariable Long listId) {
        return ResponseEntity.ok(listsMapper.pinList(listId));
    }

    @GetMapping("/add/user/{userId}")
    public ResponseEntity<List<SimpleListResponse>> getListsToAddUser(@PathVariable Long userId) {
        return ResponseEntity.ok(listsMapper.getListsToAddUser(userId));
    }

    @PostMapping("/add/user")
    public ResponseEntity<String> addUserToLists(@RequestBody UserToListsRequest userToListsRequest) {
        return ResponseEntity.ok(listsMapper.addUserToLists(userToListsRequest));
    }

    @GetMapping("/add/user/{userId}/{listId}")
    public ResponseEntity<Boolean> addUserToList(@PathVariable Long userId, @PathVariable Long listId) {
        NotificationResponse notification = listsMapper.addUserToList(userId, listId);

        if (notification.getId() != null) {
            messagingTemplate.convertAndSend("/topic/notifications/" + notification.getUser().getId(), notification);
        }
        return ResponseEntity.ok(notification.isAddedToList());
    }

    @GetMapping("/{listId}/tweets")
    public ResponseEntity<List<TweetResponse>> getTweetsByListId(@PathVariable Long listId, @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = listsMapper.getTweetsByListId(listId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/{listId}/details")
    public ResponseEntity<BaseListResponse> getListDetails(@PathVariable Long listId) {
        return ResponseEntity.ok(listsMapper.getListDetails(listId));
    }

    @GetMapping("/{listId}/{listOwnerId}/followers")
    public ResponseEntity<List<ListMemberResponse>> getListFollowers(@PathVariable Long listId, @PathVariable Long listOwnerId) {
        return ResponseEntity.ok(listsMapper.getListFollowers(listId, listOwnerId));
    }

    @GetMapping("/{listId}/{listOwnerId}/members")
    public ResponseEntity<List<?>> getListMembers(@PathVariable Long listId, @PathVariable Long listOwnerId) {
        return ResponseEntity.ok(listsMapper.getListMembers(listId, listOwnerId));
    }

    @GetMapping("/search/{listId}/{username}")
    public ResponseEntity<List<ListsOwnerMemberResponse>> searchListMembersByUsername(
            @PathVariable Long listId, @PathVariable String username) {
        return ResponseEntity.ok(listsMapper.searchListMembersByUsername(listId, username));
    }
}