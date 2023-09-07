package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.request.ListsRequest;
import com.gmail.javacoded78.dto.request.UserToListsRequest;
import com.gmail.javacoded78.dto.response.BaseListResponse;
import com.gmail.javacoded78.dto.response.lists.ListMemberResponse;
import com.gmail.javacoded78.dto.response.ListResponse;
import com.gmail.javacoded78.dto.response.ListUserResponse;
import com.gmail.javacoded78.dto.response.PinnedListResponse;
import com.gmail.javacoded78.dto.response.SimpleListResponse;
import com.gmail.javacoded78.mapper.ListsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.ADD_USER;
import static com.gmail.javacoded78.constants.PathConstants.ADD_USER_LIST_ID;
import static com.gmail.javacoded78.constants.PathConstants.ADD_USER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOW_LIST_ID;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID_DETAILS;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID_FOLLOWERS;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID_MEMBERS;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.PINED;
import static com.gmail.javacoded78.constants.PathConstants.PIN_LIST_ID;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_LIST_ID;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_LISTS;
import static com.gmail.javacoded78.constants.PathConstants.USER;
import static com.gmail.javacoded78.constants.PathConstants.USER_CONSIST;
import static com.gmail.javacoded78.constants.PathConstants.USER_USER_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_LISTS)
public class ListsController {

    private final ListsMapper listsMapper;

    @GetMapping
    public ResponseEntity<List<ListResponse>> getAllTweetLists() {
        return ResponseEntity.ok(listsMapper.getAllTweetLists());
    }

    @GetMapping(USER)
    public ResponseEntity<List<ListUserResponse>> getUserTweetLists() {
        return ResponseEntity.ok(listsMapper.getUserTweetLists());
    }

    @GetMapping(USER_USER_ID)
    public ResponseEntity<List<ListResponse>> getUserTweetListsById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(listsMapper.getUserTweetListsById(userId));
    }

    @GetMapping(USER_CONSIST)
    public ResponseEntity<List<ListResponse>> getTweetListsWhichUserIn() {
        return ResponseEntity.ok(listsMapper.getTweetListsWhichUserIn());
    }

    @GetMapping(PINED)
    public ResponseEntity<List<PinnedListResponse>> getUserPinnedLists() {
        return ResponseEntity.ok(listsMapper.getUserPinnedLists());
    }

    @GetMapping(LIST_ID)
    public ResponseEntity<BaseListResponse> getListById(@PathVariable("listId") Long listId) {
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

    @DeleteMapping(LIST_ID)
    public ResponseEntity<String> deleteList(@PathVariable("listId") Long listId) {
        return ResponseEntity.ok(listsMapper.deleteList(listId));
    }

    @GetMapping(FOLLOW_LIST_ID)
    public ResponseEntity<ListUserResponse> followList(@PathVariable("listId") Long listId) {
        return ResponseEntity.ok(listsMapper.followList(listId));
    }

    @GetMapping(PIN_LIST_ID)
    public ResponseEntity<PinnedListResponse> pinList(@PathVariable("listId") Long listId) {
        return ResponseEntity.ok(listsMapper.pinList(listId));
    }

    @GetMapping(ADD_USER_USER_ID)
    public ResponseEntity<List<SimpleListResponse>> getListsToAddUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(listsMapper.getListsToAddUser(userId));
    }

    @PostMapping(ADD_USER)
    public ResponseEntity<String> addUserToLists(@RequestBody UserToListsRequest userToListsRequest) {
        return ResponseEntity.ok(listsMapper.addUserToLists(userToListsRequest));
    }

    @GetMapping(ADD_USER_LIST_ID)
    public ResponseEntity<Boolean> addUserToList(@PathVariable("userId") Long userId, @PathVariable("listId") Long listId) {
        return ResponseEntity.ok(listsMapper.addUserToList(userId, listId));
    }

    @GetMapping(LIST_ID_TWEETS)
    public ResponseEntity<List<TweetResponse>> getTweetsByListId(@PathVariable("listId") Long listId,
                                                                 @PageableDefault Pageable pageable) {
        HeaderResponse<TweetResponse> response = listsMapper.getTweetsByListId(listId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(LIST_ID_DETAILS)
    public ResponseEntity<BaseListResponse> getListDetails(@PathVariable("listId") Long listId) {
        return ResponseEntity.ok(listsMapper.getListDetails(listId));
    }

    @GetMapping(LIST_ID_FOLLOWERS)
    public ResponseEntity<List<ListMemberResponse>> getListFollowers(@PathVariable("listId") Long listId,
                                                                     @PathVariable("listOwnerId") Long listOwnerId) {
        return ResponseEntity.ok(listsMapper.getListFollowers(listId, listOwnerId));
    }

    @GetMapping(LIST_ID_MEMBERS)
    public ResponseEntity<List<ListMemberResponse>> getListMembers(@PathVariable("listId") Long listId,
                                                                   @PathVariable("listOwnerId") Long listOwnerId) {
        return ResponseEntity.ok(listsMapper.getListMembers(listId, listOwnerId));
    }

    @GetMapping(SEARCH_LIST_ID)
    public ResponseEntity<List<ListMemberResponse>> searchListMembersByUsername(@PathVariable("listId") Long listId,
                                                                                @PathVariable("username") String username) {
        return ResponseEntity.ok(listsMapper.searchListMembersByUsername(listId, username));
    }
}
