package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.model.Notification;
import com.gmail.javacoded78.service.NotificationClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationClientMapper {

    private final BasicMapper basicMapper;
    private final NotificationClientService notificationClientService;

    public NotificationResponse sendNotification(NotificationRequest request) {
        Notification notification = basicMapper.convertToResponse(request, Notification.class);
        return notificationClientService.sendNotification(notification, request.isNotificationCondition());
    }

    public void sendTweetNotificationToSubscribers(Long tweetId) {
        notificationClientService.sendTweetNotificationToSubscribers(tweetId);
    }
}