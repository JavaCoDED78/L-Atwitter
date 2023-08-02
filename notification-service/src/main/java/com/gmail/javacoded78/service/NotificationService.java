package com.gmail.javacoded78.service;


import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.repository.projection.NotificationInfoProjection;
import com.gmail.javacoded78.repository.projection.NotificationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {

    Page<NotificationProjection> getUserNotifications(Pageable pageable);

    List<NotificationUserResponse> getTweetAuthorsNotifications();

    NotificationInfoProjection getUserNotificationById(Long notificationId);

    HeaderResponse<TweetResponse> getNotificationsFromTweetAuthors(Pageable pageable);
}
