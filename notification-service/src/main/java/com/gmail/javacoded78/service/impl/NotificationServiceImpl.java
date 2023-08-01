package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.IdsRequest;
import com.gmail.javacoded78.dto.TweetResponse;
import com.gmail.javacoded78.dto.UserResponse;
import com.gmail.javacoded78.dto.notification.NotificationListResponse;
import com.gmail.javacoded78.dto.notification.NotificationResponse;
import com.gmail.javacoded78.dto.notification.NotificationTweetResponse;
import com.gmail.javacoded78.dto.notification.NotificationUserResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.ListsClient;
import com.gmail.javacoded78.feign.TweetClient;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.mapper.BasicMapper;
import com.gmail.javacoded78.model.Notification;
import com.gmail.javacoded78.repository.NotificationRepository;
import com.gmail.javacoded78.repository.projection.NotificationInfoProjection;
import com.gmail.javacoded78.repository.projection.NotificationProjection;
import com.gmail.javacoded78.service.NotificationService;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserClient userClient;
    private final TweetClient tweetClient;
    private final ListsClient listsClient;

    @Override
    @Transactional
    public Page<NotificationProjection> getUserNotifications(Pageable pageable) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        userClient.resetNotificationCount();
        return notificationRepository.getNotificationsByUserId(userId, pageable);
    }

    @Override
    @Transactional
    public List<NotificationUserResponse> getTweetAuthorsNotifications() {
        userClient.resetNotificationCount();
        return userClient.getUsersWhichUserSubscribed();
    }

    @Override
    public NotificationInfoProjection getUserNotificationById(Long notificationId) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        return notificationRepository.getUserNotificationById(userId, notificationId)
                .orElseThrow(() -> new ApiRequestException("Notification not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public HeaderResponse<TweetResponse> getNotificationsFromTweetAuthors(Pageable pageable) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        List<Long> userIds = userClient.getUserIdsWhichUserSubscribed();
        List<Long> tweetIds = notificationRepository.getTweetIdsByNotificationType(userIds, authUserId);
        return tweetClient.getTweetsByIds(new IdsRequest(tweetIds), pageable);
    }

    public UserResponse getUserById(Long userId) {
        return userClient.getUserById(userId);
    }

    public TweetResponse getTweetById(Long tweetId) {
        return tweetClient.getTweetById(tweetId);
    }

    public NotificationUserResponse getNotificationUser(Long userId) {
        return userClient.getNotificationUser(userId);
    }

    public NotificationTweetResponse getNotificationTweet(Long userId) {
        return tweetClient.getNotificationTweet(userId);
    }

    public NotificationListResponse getNotificationList(Long listId) {
        return listsClient.getNotificationList(listId);
    }
}
