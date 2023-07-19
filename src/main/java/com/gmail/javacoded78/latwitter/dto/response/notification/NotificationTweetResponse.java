package com.gmail.javacoded78.latwitter.dto.response.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationTweetResponse {

    private Long id;
    private String text;
    private NotificationCommonUserResponse user;
    private boolean notificationCondition;
}
