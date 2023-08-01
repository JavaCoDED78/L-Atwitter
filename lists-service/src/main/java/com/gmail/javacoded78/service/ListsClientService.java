package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.notification.NotificationListResponse;

public interface ListsClientService {

    NotificationListResponse getNotificationList(Long listId);
}
