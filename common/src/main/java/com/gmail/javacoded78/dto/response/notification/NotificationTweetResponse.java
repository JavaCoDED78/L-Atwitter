package com.gmail.javacoded78.dto.response.notification;

import lombok.Data;

@Data
public class NotificationTweetResponse {

    private Long id;
    private String text;
    private Long authorId;
    private boolean notificationCondition;
}
