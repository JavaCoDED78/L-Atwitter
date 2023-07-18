package com.gmail.javacoded78.latwitter.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class NotificationsResponse {

    private List<NotificationResponse> notifications;
    private List<UserResponse> tweetAuthors;
}
