package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.notification.NotificationResponse;
import com.gmail.javacoded78.model.Notification;

public interface NotificationService {

    NotificationResponse sendListNotification(Notification notification, boolean isAddedToList);

    NotificationResponse sendTweetNotification(Notification notification, boolean isTweetLiked);

    void sendTweetNotificationToSubscribers(Long tweetId);
}
