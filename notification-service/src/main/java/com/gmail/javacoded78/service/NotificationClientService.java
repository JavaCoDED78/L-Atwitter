package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.model.Notification;

public interface NotificationClientService {

    NotificationResponse sendNotification(Notification notification, boolean notificationCondition);

    void sendTweetMentionNotification(Notification notification);

    void sendTweetNotificationToSubscribers(Long tweetId);
}
