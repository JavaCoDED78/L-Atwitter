package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.dto.TweetResponse;
import com.gmail.javacoded78.dto.UserResponse;
import com.gmail.javacoded78.enums.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationInfoResponse {

    private Long id;
    private LocalDateTime date;
    private NotificationType notificationType;
    private UserResponse user;
    private TweetResponse tweet;
}