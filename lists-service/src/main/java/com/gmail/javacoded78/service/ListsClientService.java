package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.response.notification.NotificationListResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetListResponse;

public interface ListsClientService {

    NotificationListResponse getNotificationList(Long listId);

    TweetListResponse getTweetList(Long listId);
}
