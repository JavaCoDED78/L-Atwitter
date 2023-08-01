package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.NotificationRequest;
import com.gmail.javacoded78.dto.notification.NotificationResponse;
import com.gmail.javacoded78.model.Notification;
import com.gmail.javacoded78.service.NotificationClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationClientMapper {

    private final BasicMapper basicMapper;
    private final NotificationClientService notificationClientService;

    public NotificationResponse sendListNotification(NotificationRequest request) {
        Notification notification = basicMapper.convertToResponse(request, Notification.class);
        return notificationClientService.sendListNotification(notification, request.isNotificationCondition());
    }

    public NotificationResponse sendUserNotification(NotificationRequest request) {
        Notification notification = basicMapper.convertToResponse(request, Notification.class);
        return notificationClientService.sendUserNotification(notification, request.isNotificationCondition());
    }

    public NotificationResponse sendTweetNotification(NotificationRequest request) {
        Notification notification = basicMapper.convertToResponse(request, Notification.class);
        return notificationClientService.sendTweetNotification(notification, request.isNotificationCondition());
    }

    public void sendTweetNotificationToSubscribers(Long tweetId) {
        notificationClientService.sendTweetNotificationToSubscribers(tweetId);
    }
}