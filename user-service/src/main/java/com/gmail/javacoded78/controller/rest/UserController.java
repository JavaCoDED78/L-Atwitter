package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.SearchTermsRequest;
import com.gmail.javacoded78.dto.response.AuthenticationResponse;
import com.gmail.javacoded78.dto.response.SearchResultResponse;
import com.gmail.javacoded78.dto.response.lists.CommonUserResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.request.UserRequest;
import com.gmail.javacoded78.dto.response.AuthUserResponse;
import com.gmail.javacoded78.dto.response.UserDetailResponse;
import com.gmail.javacoded78.dto.response.UserProfileResponse;
import com.gmail.javacoded78.mapper.AuthenticationMapper;
import com.gmail.javacoded78.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.ALL;
import static com.gmail.javacoded78.constants.PathConstants.DETAILS_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.PIN_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.RELEVANT;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_RESULTS;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_TEXT;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_USERNAME;
import static com.gmail.javacoded78.constants.PathConstants.START;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBE_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.TOKEN;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_USER;
import static com.gmail.javacoded78.constants.PathConstants.USER_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_USER)
public class UserController {

    private final UserMapper userMapper;
    private final AuthenticationMapper authenticationMapper;

    @GetMapping(TOKEN)
    public ResponseEntity<AuthenticationResponse> getUserByToken() {
        return ResponseEntity.ok(authenticationMapper.getUserByToken());
    }

    @GetMapping(USER_ID)
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.getUserById(userId));
    }

    @GetMapping(ALL)
    public ResponseEntity<List<UserResponse>> getUsers(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = userMapper.getUsers(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(SEARCH_TEXT)
    public ResponseEntity<SearchResultResponse> searchByText(@PathVariable String text) {
        return ResponseEntity.ok(userMapper.searchByText(text));
    }

    @PostMapping(SEARCH_RESULTS)
    public ResponseEntity<List<CommonUserResponse>> getSearchResults(@RequestBody SearchTermsRequest request) {
        return ResponseEntity.ok(userMapper.getSearchResults(request));
    }

    @GetMapping(RELEVANT)
    public ResponseEntity<List<UserResponse>> getRelevantUsers() {
        return ResponseEntity.ok(userMapper.getRelevantUsers());
    }

    @GetMapping(SEARCH_USERNAME)
    public ResponseEntity<List<UserResponse>> searchUsersByUsername(@PathVariable String username,
                                                                    @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = userMapper.searchUsersByUsername(username, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(START)
    public ResponseEntity<Boolean> startUseTwitter() {
        return ResponseEntity.ok(userMapper.startUseTwitter());
    }

    @PutMapping
    public ResponseEntity<AuthUserResponse> updateUserProfile(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userMapper.updateUserProfile(userRequest));
    }

    @GetMapping(SUBSCRIBE_USER_ID)
    public ResponseEntity<Boolean> processSubscribeToNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.processSubscribeToNotifications(userId));
    }

    @GetMapping(PIN_TWEET_ID)
    public ResponseEntity<Long> processPinTweet(@PathVariable Long tweetId) {
        return ResponseEntity.ok(userMapper.processPinTweet(tweetId));
    }

    @GetMapping(DETAILS_USER_ID)
    public ResponseEntity<UserDetailResponse> getUserDetails(@PathVariable Long userId) {
        return ResponseEntity.ok(userMapper.getUserDetails(userId));
    }
}
