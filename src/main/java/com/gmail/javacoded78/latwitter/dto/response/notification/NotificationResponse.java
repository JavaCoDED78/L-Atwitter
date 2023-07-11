package com.gmail.javacoded78.latwitter.dto.response.notification;

import com.gmail.javacoded78.latwitter.model.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {

    private Long id;
    private LocalDateTime date;
    private NotificationType notificationType;
    private NotificationUserResponse user;
    private NotificationTweetResponse tweet;
}
