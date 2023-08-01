package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.notification.NotificationUserResponse;
import com.gmail.javacoded78.mapper.BasicMapper;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projection.AuthNotificationUserProjection;
import com.gmail.javacoded78.repository.projection.ChatTweetUserProjection;
import com.gmail.javacoded78.repository.projection.ChatUserParticipantProjection;
import com.gmail.javacoded78.repository.projection.ListMemberProjection;
import com.gmail.javacoded78.repository.projection.NotificationUserProjection;
import com.gmail.javacoded78.repository.projection.TweetAdditionalInfoUserProjection;
import com.gmail.javacoded78.repository.projection.TweetAuthorProjection;
import com.gmail.javacoded78.repository.projection.UserSubscriberProjection;
import com.gmail.javacoded78.service.AuthenticationService;
import com.gmail.javacoded78.service.UserClientService;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserClientServiceImpl implements UserClientService {

    private final UserRepository userRepository;
    private final BasicMapper basicMapper;
    private final AuthenticationService authenticationService;
    private final UserServiceImpl userService;

//    @Override
//    public Optional<User> getUserById(Long userId) {
//        return userRepository.findById(userId);
//    }
//
//    @Override
//    public List<User> getUsersByIds(UserIdsRequest request) {
//        return userRepository.findByIdIn(request.getUserIds());
//    }
//
//    @Override
//    public List<Long> getUserFollowersIds() {
//        Long authUserId = authenticationService.getAuthenticatedUserId();
//        List<Long> userFollowersIds = userRepository.getUserFollowersIds(authUserId);
//        userFollowersIds.add(authUserId);
//        return userFollowersIds;
//    }
//
//    @Override
//    public HeaderResponse<UserChatResponse> searchUsersByUsername(String username, Pageable pageable) {
//        Page<UserChatProjection> users = userRepository.findByFullNameOrUsername(username, pageable, UserChatProjection.class);
//        return basicMapper.getHeaderResponse(users, UserChatResponse.class);
//    }
//
//    @Override
//    public User getValidUser(Long userId, Long authUserId) {
//        NotificationUserProjection user = userRepository.getValidUser(userId, authUserId)
//                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
//        return basicMapper.convertToResponse(user, User.class);
//    }
//
//    @Override
//    public User getAuthNotificationUser(Long authUserId) {
//        AuthNotificationUserProjection user = userRepository.getAuthNotificationUser(authUserId);
//        return basicMapper.convertToResponse(user, User.class);
//    }
//
//    @Override
//    public List<Long> getSubscribersByUserId(Long userId) {
//        return userRepository.getSubscribersByUserId(userId);
//    }
//
//    @Override
//    public Boolean isUserFollowByOtherUser(Long userId) {
//        return userService.isUserFollowByOtherUser(userId);
//    }
//
//    @Override
//    public Boolean isUserHavePrivateProfile(Long userId) {
//        return userService.isUserHavePrivateProfile(userId);
//    }
//
//    @Override
//    public Boolean isUserMutedByMyProfile(Long userId) {
//        return userService.isUserMutedByMyProfile(userId);
//    }
//
//    @Override
//    public Boolean isUserBlocked(Long userId, Long supposedBlockedUserId) {
//        return userRepository.isUserBlocked(userId, supposedBlockedUserId);
//    }
//
//    @Override
//    public Boolean isUserBlockedByMyProfile(Long userId) {
//        return userService.isUserBlockedByMyProfile(userId);
//    }
//
//    @Override
//    public Boolean isMyProfileBlockedByUser(Long userId) {
//        return userService.isMyProfileBlockedByUser(userId);
//    }
//
//    @Override
//    public Boolean isMyProfileWaitingForApprove(Long userId) {
//        return userService.isMyProfileWaitingForApprove(userId);
//    }
//
//    @Override
//    @Transactional
//    public void increaseNotificationsCount(Long userId) {
//        userRepository.increaseNotificationsCount(userId);
//    }
//
//    @Override
//    @Transactional
//    public void updateLikeCount(boolean increaseCount) {
//        Long userId = AuthUtil.getAuthenticatedUserId();
//        userRepository.updateLikeCount(increaseCount, userId);
//    }
//
//    @Override
//    @Transactional
//    public void updateTweetCount(boolean increaseCount) {
//        Long userId = AuthUtil.getAuthenticatedUserId();
//        userRepository.updateTweetCount(increaseCount, userId);
//    }
//
//    @Override
//    @Transactional
//    public void updateMediaTweetCount(boolean increaseCount) {
//        Long userId = AuthUtil.getAuthenticatedUserId();
//        userRepository.updateMediaTweetCount(increaseCount, userId);
//    }
//
//    @Override
//    @Transactional
//    public void saveUser(User user) {
//        userRepository.save(user);
//    }
//
//    // NEW
//    @Override
//    public ListOwnerResponse getListOwnerById(Long userId) {
//        ListOwnerProjection user = userRepository.getListOwnerById(userId);
//        return basicMapper.convertToResponse(user, ListOwnerResponse.class);
//    }
//
//    @Override
//    public List<ListMemberResponse> getListParticipantsByIds(UserIdsRequest request) {
//        List<ListMemberProjection> users = userRepository.getUsersByIds(request.getUserIds());
//        return basicMapper.convertToResponseList(users, ListMemberResponse.class);
//    }
//
//    @Override
//    public List<ListMemberResponse> searchListMembersByUsername(String username) {
//        List<ListMemberProjection> users = userRepository.searchListMembersByUsername(username);
//        return basicMapper.convertToResponseList(users, ListMemberResponse.class);
//    }

    @Override
    public NotificationUserResponse getNotificationUser(Long userId) {
        NotificationUserProjection user = userRepository.getNotificationUser(userId);
        return basicMapper.convertToResponse(user, NotificationUserResponse.class);
    }

//    @Override
//    public TweetAuthorResponse getTweetAuthor(Long userId) {
//        TweetAuthorProjection user = userRepository.getTweetAuthor(userId);
//        return basicMapper.convertToResponse(user, TweetAuthorResponse.class);
//    }
//
//    @Override
//    public TweetAdditionalInfoUserResponse getTweetAdditionalInfoUser(Long userId) {
//        TweetAdditionalInfoUserProjection user = userRepository.getTweetAdditionalInfoUser(userId);
//        return basicMapper.convertToResponse(user, TweetAdditionalInfoUserResponse.class);
//    }
//
//    @Override
//    public HeaderResponse<UserResponse> getTweetLikedUsersByIds(UserIdsRequest request, Pageable pageable) {
//        Page<UserProjection> users = userRepository.getTweetLikedUsersByIds(request.getUserIds(), pageable);
//        return basicMapper.getHeaderResponse(users, UserResponse.class);
//    }
//
//    @Override
//    public HeaderResponse<UserResponse> getRetweetedUsersByTweetId(UserIdsRequest request, Pageable pageable) {
//        Page<UserProjection> users = userRepository.getRetweetedUsersByTweetId(request.getUserIds(), pageable);
//        return basicMapper.getHeaderResponse(users, UserResponse.class);
//    }
//
//    @Override
//    @Transactional
//    public void updatePinnedTweetId(Long tweetId) {
//        Long userId = AuthUtil.getAuthenticatedUserId();
//        Long pinnedTweetId = userRepository.getPinnedTweetId(userId);
//
//        if (pinnedTweetId != null && pinnedTweetId.equals(tweetId)) {
//            userRepository.updatePinnedTweetId(null, userId);
//        }
//    }
//
//    @Override
//    public List<Long> getValidUserIds(UserIdsRequest request, String text) {
//        List<Long> validUserIds = userRepository.getValidUserIdsByIds(request.getUserIds());
//        List<Long> userIdsByUsername = userRepository.getValidUserIdsByName(text, request.getUserIds());
//        return Stream.concat(validUserIds.stream(), userIdsByUsername.stream()).distinct().toList();
//    }
//
//    @Override
//    public ChatUserParticipantResponse getChatParticipant(Long userId) {
//        ChatUserParticipantProjection user = userRepository.getChatParticipant(userId);
//        return basicMapper.convertToResponse(user, ChatUserParticipantResponse.class);
//    }
//
//    @Override
//    public Boolean isUserExists(Long userId) {
//        return userRepository.isUserExists(userId);
//    }
//
//    @Override
//    public UserResponse getUserResponseById(Long userId) {
//        UserProjection user = userRepository.getUserResponseById(userId);
//        return basicMapper.convertToResponse(user, UserResponse.class);
//    }
//
//    @Override
//    public ChatTweetUserResponse getChatTweetUser(Long userId) {
//        ChatTweetUserProjection user = userRepository.getChatTweetUser(userId);
//        return basicMapper.convertToResponse(user, ChatTweetUserResponse.class);
//    }
//
//    @Override
//    public List<Long> validateChatUsersIds(UserIdsRequest request) {
//        Long authUserId = AuthUtil.getAuthenticatedUserId();
//        List<Long> blockedUserIds = userRepository.geUserIdsWhoBlockedMyProfile(request.getUserIds(), authUserId);
//        request.getUserIds().removeAll(blockedUserIds);
//        return request.getUserIds();
//    }

    // NEW
    @Override
    public List<NotificationUserResponse> getUsersWhichUserSubscribed() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        List<NotificationUserProjection> users = userRepository.getUsersWhichUserSubscribed(authUserId);
        return basicMapper.convertToResponseList(users, NotificationUserResponse.class);
    }

    @Override
    public List<Long> getUserIdsWhichUserSubscribed() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.getUserIdsWhichUserSubscribed(authUserId);
    }

    @Override
    @Transactional
    public void resetNotificationCount() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        userRepository.resetNotificationCount(authUserId);
    }
}
