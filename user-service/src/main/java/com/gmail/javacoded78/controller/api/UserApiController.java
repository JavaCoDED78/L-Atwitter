package com.gmail.javacoded78.controller.api;


import com.gmail.javacoded78.dto.response.chat.ChatTweetUserResponse;
import com.gmail.javacoded78.dto.response.chat.ChatUserParticipantResponse;
import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetAdditionalInfoUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetAuthorResponse;
import com.gmail.javacoded78.dto.response.user.UserChatResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.response.lists.ListMemberResponse;
import com.gmail.javacoded78.dto.response.lists.ListOwnerResponse;
import com.gmail.javacoded78.service.UserClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_USER;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_PARTICIPANT_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_VALID_IDS;
import static com.gmail.javacoded78.constants.PathConstants.IDS;
import static com.gmail.javacoded78.constants.PathConstants.IS_BLOCKED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_EXISTS_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_FOLLOWED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_MY_PROFILE_BLOCKED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_PRIVATE_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IS_USER_BLOCKED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.LIKE_COUNT;
import static com.gmail.javacoded78.constants.PathConstants.LIST_OWNER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.LIST_PARTICIPANTS;
import static com.gmail.javacoded78.constants.PathConstants.LIST_PARTICIPANTS_USERNAME;
import static com.gmail.javacoded78.constants.PathConstants.MEDIA_COUNT;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_RESET;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_USER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_USERNAME;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBERS;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBERS_IDS;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBERS_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ADDITIONAL_INFO_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_AUTHOR_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_COUNT;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_LIKED;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_PINNED_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_PINNED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_RETWEETED;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_VALID_IDS;
import static com.gmail.javacoded78.constants.PathConstants.USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.VALID_IDS;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_USER)
public class UserApiController {

    private final UserClientService userService;

    @GetMapping(IDS)
    public List<Long> getUserFollowersIds() {
        return userService.getUserFollowersIds();
    }

    @GetMapping(SEARCH_USERNAME)
    public HeaderResponse<UserChatResponse> searchUsersByUsername(@PathVariable("username") String username,
                                                                  Pageable pageable) {
        return userService.searchUsersByUsername(username, pageable);
    }

    @GetMapping(SUBSCRIBERS_USER_ID)
    public List<Long> getSubscribersByUserId(@PathVariable("userId") Long userId) {
        return userService.getSubscribersByUserId(userId);
    }

    @GetMapping(IS_FOLLOWED_USER_ID)
    public Boolean isUserFollowByOtherUser(@PathVariable("userId") Long userId) {
        return userService.isUserFollowByOtherUser(userId);
    }

    @GetMapping(IS_PRIVATE_USER_ID)
    public Boolean isUserHavePrivateProfile(@PathVariable("userId") Long userId) {
        return userService.isUserHavePrivateProfile(userId);
    }

    @GetMapping(IS_BLOCKED_USER_ID)
    public Boolean isUserBlocked(@PathVariable("userId") Long userId, @PathVariable("blockedUserId") Long blockedUserId) {
        return userService.isUserBlocked(userId, blockedUserId);
    }

    @GetMapping(IS_USER_BLOCKED_USER_ID)
    public Boolean isUserBlockedByMyProfile(@PathVariable("userId") Long userId) {
        return userService.isUserBlockedByMyProfile(userId);
    }

    @GetMapping(IS_MY_PROFILE_BLOCKED_USER_ID)
    public Boolean isMyProfileBlockedByUser(@PathVariable("userId") Long userId) {
        return userService.isMyProfileBlockedByUser(userId);
    }

    @GetMapping(NOTIFICATION_USER_ID)
    public void increaseNotificationsCount(@PathVariable("userId") Long userId) {
        userService.increaseNotificationsCount(userId);
    }

    @PutMapping(LIKE_COUNT)
    public void updateLikeCount(@PathVariable("increaseCount") boolean increaseCount) {
        userService.updateLikeCount(increaseCount);
    }

    @PutMapping(TWEET_COUNT)
    public void updateTweetCount(@PathVariable("increaseCount") boolean increaseCount) {
        userService.updateTweetCount(increaseCount);
    }

    @PutMapping(MEDIA_COUNT)
    public void updateMediaTweetCount(@PathVariable("increaseCount") boolean increaseCount) {
        userService.updateMediaTweetCount(increaseCount);
    }

    @GetMapping(LIST_OWNER_USER_ID)
    public ListOwnerResponse getListOwnerById(@PathVariable("userId") Long userId) {
        return userService.getListOwnerById(userId);
    }

    @PostMapping(LIST_PARTICIPANTS)
    public List<ListMemberResponse> getListParticipantsByIds(@RequestBody IdsRequest request) {
        return userService.getListParticipantsByIds(request);
    }

    @GetMapping(LIST_PARTICIPANTS_USERNAME)
    public List<ListMemberResponse> searchListMembersByUsername(@PathVariable("username") String username) {
        return userService.searchListMembersByUsername(username);
    }

    @GetMapping(NOTIFICATION_USER_USER_ID)
    public NotificationUserResponse getNotificationUser(@PathVariable("userId") Long userId) {
        return userService.getNotificationUser(userId);
    }

    @GetMapping(TWEET_AUTHOR_USER_ID)
    public TweetAuthorResponse getTweetAuthor(@PathVariable("userId") Long userId) {
        return userService.getTweetAuthor(userId);
    }

    @GetMapping(TWEET_ADDITIONAL_INFO_USER_ID)
    public TweetAdditionalInfoUserResponse getTweetAdditionalInfoUser(@PathVariable("userId") Long userId) {
        return userService.getTweetAdditionalInfoUser(userId);
    }

    @PostMapping(TWEET_LIKED)
    public HeaderResponse<UserResponse> getTweetLikedUsersByIds(@RequestBody IdsRequest request,
                                                                @SpringQueryMap Pageable pageable) {
        return userService.getTweetLikedUsersByIds(request, pageable);
    }

    @PostMapping(TWEET_RETWEETED)
    public HeaderResponse<UserResponse> getRetweetedUsersByTweetId(@RequestBody IdsRequest request,
                                                                   @SpringQueryMap Pageable pageable) {
        return userService.getRetweetedUsersByTweetId(request, pageable);
    }

    @PutMapping(TWEET_PINNED_TWEET_ID)
    public void updatePinnedTweetId(@PathVariable("tweetId") Long tweetId) {
        userService.updatePinnedTweetId(tweetId);
    }

    @GetMapping(TWEET_PINNED_USER_ID)
    public Long getUserPinnedTweetId(@PathVariable("userId") Long userId) {
        return userService.getUserPinnedTweetId(userId);
    }

    @PostMapping(TWEET_VALID_IDS)
    public List<Long> getValidTweetUserIds(@RequestBody IdsRequest request, @PathVariable("text") String text) {
        return userService.getValidTweetUserIds(request, text);
    }

    @PostMapping(VALID_IDS)
    public List<Long> getValidUserIds(@RequestBody IdsRequest request) {
        return userService.getValidUserIds(request);
    }

    @GetMapping(CHAT_PARTICIPANT_USER_ID)
    public ChatUserParticipantResponse getChatParticipant(@PathVariable("userId") Long userId) {
        return userService.getChatParticipant(userId);
    }

    @GetMapping(IS_EXISTS_USER_ID)
    public Boolean isUserExists(@PathVariable("userId") Long userId) {
        return userService.isUserExists(userId);
    }

    @GetMapping(USER_ID)
    public UserResponse getUserById(@PathVariable("userId") Long userId) {
        return userService.getUserResponseById(userId);
    }

    @GetMapping(CHAT_USER_ID)
    public ChatTweetUserResponse getChatTweetUser(@PathVariable("userId") Long userId) {
        return userService.getChatTweetUser(userId);
    }

    @PostMapping(CHAT_VALID_IDS)
    public List<Long> validateChatUsersIds(@RequestBody IdsRequest request) {
        return userService.validateChatUsersIds(request);
    }

    @GetMapping(SUBSCRIBERS)
    public List<NotificationUserResponse> getUsersWhichUserSubscribed() {
        return userService.getUsersWhichUserSubscribed();
    }

    @GetMapping(SUBSCRIBERS_IDS)
    public List<Long> getUserIdsWhichUserSubscribed() {
        return userService.getUserIdsWhichUserSubscribed();
    }

    @GetMapping(NOTIFICATION_RESET)
    public void resetNotificationCount() {
        userService.resetNotificationCount();
    }
}
