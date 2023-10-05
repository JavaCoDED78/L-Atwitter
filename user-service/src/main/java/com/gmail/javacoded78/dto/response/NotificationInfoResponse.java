package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationInfoResponse {

    private Long id;
    private LocalDateTime date;
    private NotificationType notificationType;
    private UserResponse user;
    private TweetResponse tweet;
}