package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.common.dto.TweetResponse;
import com.gmail.javacoded78.common.dto.UserResponse;
import com.gmail.javacoded78.common.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationInfoResponse {

    private Long id;
    private LocalDateTime date;
    private NotificationType notificationType;
    private UserResponse user;
    private TweetResponse tweet;
}