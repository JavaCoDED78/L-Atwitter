package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.dto.TweetResponse;
import com.gmail.javacoded78.dto.UserResponse;
import com.gmail.javacoded78.enums.NotificationType;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface NotificationInfoProjection {
    Long getId();
    LocalDateTime getDate();
    NotificationType getNotificationType();
    Long getUserId();
    Long getTweetId();

    @Value("#{target.userId == null ? null : @notificationServiceImpl.getUserById(target.userId)}")
    UserResponse getUser();

    @Value("#{target.tweetId == null ? null : @notificationServiceImpl.getTweetById(target.tweetId)}")
    TweetResponse getTweet();
}
