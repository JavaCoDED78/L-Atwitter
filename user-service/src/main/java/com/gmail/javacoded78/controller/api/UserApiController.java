package com.gmail.javacoded78.controller.api;


import com.gmail.javacoded78.common.dto.HeaderResponse;
import com.gmail.javacoded78.common.dto.NotificationUserResponse;
import com.gmail.javacoded78.common.dto.UserResponse;
import com.gmail.javacoded78.common.dto.common_new.ChatTweetUserResponse;
import com.gmail.javacoded78.common.dto.common_new.ChatUserParticipantResponse;
import com.gmail.javacoded78.common.dto.common_new.TweetAdditionalInfoUserResponse;
import com.gmail.javacoded78.common.dto.common_new.TweetAuthorResponse;
import com.gmail.javacoded78.common.dto.common_new.UserChatResponse;
import com.gmail.javacoded78.common.dto.common_new.UserIdsRequest;
import com.gmail.javacoded78.common.dto.common_new.ListMemberResponse;
import com.gmail.javacoded78.common.dto.common_new.ListOwnerResponse;
import com.gmail.javacoded78.common.models.User;
import com.gmail.javacoded78.common.projection.UserChatProjection;
import com.gmail.javacoded78.service.UserClientService;
import com.gmail.javacoded78.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.gmail.javacoded78.common.controller.PathConstants.API_V1_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_USER)
public class UserApiController {

    private final UserClientService userService;

//    @GetMapping("/{userId}")
//    public Optional<User> getUserById(@PathVariable("userId") Long userId) {
//        return userService.getUserById(userId);
//    }

    @PostMapping("/ids")
    public List<User> getUsersByIds(@RequestBody UserIdsRequest request) {
        return userService.getUsersByIds(request);
    }

    @GetMapping("/ids")
    public List<Long> getUserFollowersIds() {
        return userService.getUserFollowersIds();
    }

    @GetMapping("/search/{username}")
    public HeaderResponse<UserChatResponse> searchUsersByUsername(@PathVariable("username") String username, Pageable pageable) {
        return userService.searchUsersByUsername(username, pageable);
    }

    @GetMapping("/valid/{userId}/{authUserId}")
    public User getValidUser(@PathVariable("userId") Long userId, @PathVariable("authUserId") Long authUserId) {
        return userService.getValidUser(userId, authUserId);
    }

    @GetMapping("/notification/authUser/{authUserId}")
    public User getAuthNotificationUser(@PathVariable("authUserId") Long authUserId) {
        return userService.getAuthNotificationUser(authUserId);
    }

    @GetMapping("/subscribers/{userId}")
    public List<Long> getSubscribersByUserId(@PathVariable("userId") Long userId) {
        return userService.getSubscribersByUserId(userId);
    }

    @GetMapping("/is_followed/{userId}")
    public Boolean isUserFollowByOtherUser(@PathVariable("userId") Long userId) {
        return userService.isUserFollowByOtherUser(userId);
    }

    @GetMapping("/is_private/{userId}")
    public Boolean isUserHavePrivateProfile(@PathVariable("userId") Long userId) {
        return userService.isUserHavePrivateProfile(userId);
    }

    @GetMapping("/is_muted/{userId}")
    public Boolean isUserMutedByMyProfile(@PathVariable("userId") Long userId) {
        return userService.isUserMutedByMyProfile(userId);
    }

    @GetMapping("/is_blocked/{userId}/{blockedUserId}")
    public Boolean isUserBlocked(@PathVariable("userId") Long userId, @PathVariable("blockedUserId") Long blockedUserId) {
        return userService.isUserBlocked(userId, blockedUserId);
    }

    @GetMapping("/is_user_blocked/{userId}")
    public Boolean isUserBlockedByMyProfile(@PathVariable("userId") Long userId) {
        return userService.isUserBlockedByMyProfile(userId);
    }

    @GetMapping("/is_my_profile_blocked/{userId}")
    public Boolean isMyProfileBlockedByUser(@PathVariable("userId") Long userId) {
        return userService.isMyProfileBlockedByUser(userId);
    }

    @GetMapping("/is_approved/{userId}")
    public Boolean isMyProfileWaitingForApprove(@PathVariable("userId") Long userId) {
        return userService.isMyProfileWaitingForApprove(userId);
    }

    @GetMapping("/notification/{userId}")
    public void increaseNotificationsCount(@PathVariable("userId") Long userId) {
        userService.increaseNotificationsCount(userId);
    }

    @PutMapping("/like/count/{increaseCount}")
    public void updateLikeCount(@PathVariable("increaseCount") boolean increaseCount) {
        userService.updateLikeCount(increaseCount);
    }

    @PutMapping("/tweet/count/{increaseCount}")
    public void updateTweetCount(@PathVariable("increaseCount") boolean increaseCount) {
        userService.updateTweetCount(increaseCount);
    }

    @PutMapping("/media/count/{increaseCount}")
    public void updateMediaTweetCount(@PathVariable("increaseCount") boolean increaseCount) {
        userService.updateMediaTweetCount(increaseCount);
    }

    @PostMapping
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    // NEW
    @GetMapping("/list/owner/{userId}")
    public ListOwnerResponse getListOwnerById(@PathVariable("userId") Long userId) {
        return userService.getListOwnerById(userId);
    }

    @PostMapping("/list/participants")
    public List<ListMemberResponse> getListParticipantsByIds(@RequestBody UserIdsRequest request) {
        return userService.getListParticipantsByIds(request);
    }

    @GetMapping("/list/participants/{username}")
    public List<ListMemberResponse> searchListMembersByUsername(@PathVariable("username") String username) {
        return userService.searchListMembersByUsername(username);
    }

    @GetMapping("/notification/user/{userId}")
    public NotificationUserResponse getNotificationUser(@PathVariable("userId") Long userId) {
        return userService.getNotificationUser(userId);
    }

    @GetMapping("/tweet/author/{userId}")
    public TweetAuthorResponse getTweetAuthor(@PathVariable("userId") Long userId) {
        return userService.getTweetAuthor(userId);
    }

    @GetMapping("/tweet/additional/info/{userId}")
    public TweetAdditionalInfoUserResponse getTweetAdditionalInfoUser(@PathVariable("userId") Long userId) {
        return userService.getTweetAdditionalInfoUser(userId);
    }

    @PostMapping("/tweet/liked")
    public HeaderResponse<UserResponse> getTweetLikedUsersByIds(@RequestBody UserIdsRequest request,
                                                                @SpringQueryMap Pageable pageable) {
        return userService.getTweetLikedUsersByIds(request, pageable);
    }

    @PostMapping("/tweet/retweeted")
    public HeaderResponse<UserResponse> getRetweetedUsersByTweetId(@RequestBody UserIdsRequest request,
                                                                   @SpringQueryMap Pageable pageable) {
        return userService.getRetweetedUsersByTweetId(request, pageable);
    }

    @PutMapping("/tweet/pinned/{tweetId}")
    public void updatePinnedTweetId(@PathVariable("tweetId") Long tweetId) {
        userService.updatePinnedTweetId(tweetId);
    }

    @PostMapping("/tweet/valid/ids/{text}")
    public List<Long> getValidUserIds(@RequestBody UserIdsRequest request, @PathVariable("text") String text) {
        return userService.getValidUserIds(request, text);
    }

    @GetMapping("/chat/participant/{userId}")
    public ChatUserParticipantResponse getChatParticipant(@PathVariable("userId") Long userId) {
        return userService.getChatParticipant(userId);
    }

    @GetMapping("/is_exists/{userId}")
    public Boolean isUserExists(@PathVariable("userId") Long userId) {
        return userService.isUserExists(userId);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserResponseById(@PathVariable("userId") Long userId) {
        return userService.getUserResponseById(userId);
    }

    @GetMapping("/chat/{userId}")
    public ChatTweetUserResponse getChatTweetUser(@PathVariable("userId") Long userId) {
        return userService.getChatTweetUser(userId);
    }

    @PostMapping("/chat/valid/ids")
    public List<Long> validateChatUsersIds(@RequestBody UserIdsRequest request) {
        return userService.validateChatUsersIds(request);
    }
}
