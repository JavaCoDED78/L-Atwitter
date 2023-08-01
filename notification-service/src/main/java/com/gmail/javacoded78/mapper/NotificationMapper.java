package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.NotificationInfoResponse;
import com.gmail.javacoded78.dto.NotificationRequest;
import com.gmail.javacoded78.dto.TweetResponse;
import com.gmail.javacoded78.dto.notification.NotificationResponse;
import com.gmail.javacoded78.dto.notification.NotificationUserResponse;
import com.gmail.javacoded78.model.Notification;
import com.gmail.javacoded78.repository.projection.NotificationProjection;
import com.gmail.javacoded78.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    private final BasicMapper basicMapper;
    private final NotificationService notificationService;

    public HeaderResponse<NotificationResponse> getUserNotifications(Pageable pageable) {
        Page<NotificationProjection> notifications = notificationService.getUserNotifications(pageable);
        return basicMapper.getHeaderResponse(notifications, NotificationResponse.class);
    }

    public List<NotificationUserResponse> getTweetAuthorsNotifications() {
        return notificationService.getTweetAuthorsNotifications();
    }

    public NotificationInfoResponse getUserNotificationById(Long notificationId) {
        NotificationInfoProjection notification = notificationService.getUserNotificationById(notificationId);
        return basicMapper.convertToResponse(notification, NotificationInfoResponse.class);
    }

    public HeaderResponse<TweetResponse> getNotificationsFromTweetAuthors(Pageable pageable) {
        return notificationService.getNotificationsFromTweetAuthors(pageable);
//        List<TweetProjection> tweets = tweetsProjections.getContent().stream()
//                .map(TweetsProjection::getTweet)
//                .collect(Collectors.toList());
//        return basicMapper.getHeaderResponse(tweets, tweetsProjections.getTotalPages(), TweetResponse.class);
    }
}
