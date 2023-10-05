package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.SearchTermsRequest;
import com.gmail.javacoded78.dto.response.SearchResultResponse;
import com.gmail.javacoded78.dto.response.user.CommonUserResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.request.UserRequest;
import com.gmail.javacoded78.dto.response.AuthUserResponse;
import com.gmail.javacoded78.dto.response.UserDetailResponse;
import com.gmail.javacoded78.dto.response.UserProfileResponse;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.projection.AuthUserProjection;
import com.gmail.javacoded78.repository.projection.CommonUserProjection;
import com.gmail.javacoded78.repository.projection.UserDetailProjection;
import com.gmail.javacoded78.repository.projection.UserProfileProjection;
import com.gmail.javacoded78.repository.projection.UserProjection;
import com.gmail.javacoded78.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final BasicMapper basicMapper;
    private final UserService userService;

    public UserProfileResponse getUserById(Long userId) {
        UserProfileProjection user = userService.getUserById(userId);
        return basicMapper.convertToResponse(user, UserProfileResponse.class);
    }

    public HeaderResponse<UserResponse> getUsers(Pageable pageable) {
        Page<UserProjection> users = userService.getUsers(pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public List<UserResponse> getRelevantUsers() {
        List<UserProjection> users = userService.getRelevantUsers();
        return basicMapper.convertToResponseList(users, UserResponse.class);
    }

    public HeaderResponse<UserResponse> searchUsersByUsername(String username, Pageable pageable) {
        Page<UserProjection> users = userService.searchUsersByUsername(username, pageable, UserProjection.class);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    @SuppressWarnings("unchecked")
    public SearchResultResponse searchByText(String text) {
        Map<String, Object> searchResult = userService.searchByText(text);
        SearchResultResponse searchResultResponse = new SearchResultResponse();
        searchResultResponse.setText(text);
        searchResultResponse.setTweetCount((Long) searchResult.get("tweetCount"));
        searchResultResponse.setTags((List<String>) searchResult.get("tags"));
        List<CommonUserResponse> users = basicMapper.convertToResponseList(
                (List<CommonUserProjection>) searchResult.get("users"), CommonUserResponse.class);
        searchResultResponse.setUsers(users);
        return searchResultResponse;
    }

    public List<CommonUserResponse> getSearchResults(SearchTermsRequest request) {
        List<CommonUserProjection> users = userService.getSearchResults(request);
        return basicMapper.convertToResponseList(users, CommonUserResponse.class);
    }

    public Boolean startUseTwitter() {
        return userService.startUseTwitter();
    }

    public AuthUserResponse updateUserProfile(UserRequest userRequest) {
        User user = basicMapper.convertToResponse(userRequest, User.class);
        AuthUserProjection authUserProjection = userService.updateUserProfile(user);
        return basicMapper.convertToResponse(authUserProjection, AuthUserResponse.class);
    }

    public Boolean processSubscribeToNotifications(Long userId) {
        return userService.processSubscribeToNotifications(userId);
    }

    public Long processPinTweet(Long tweetId) {
        return userService.processPinTweet(tweetId);
    }

    public UserDetailResponse getUserDetails(Long userId) {
        UserDetailProjection userDetails = userService.getUserDetails(userId);
        return basicMapper.convertToResponse(userDetails, UserDetailResponse.class);
    }

    public String updateUserImage(MultipartFile file) {
        return userService.updateUserImage(file);
    }
}
