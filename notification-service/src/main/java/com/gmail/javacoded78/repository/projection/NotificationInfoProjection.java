package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.enums.NotificationType;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface NotificationInfoProjection {
    Long getId();
    LocalDateTime getDate();
    NotificationType getNotificationType();
    Long getUserId();
    Long getTweetId();

    @Value("#{target.userId == null ? null : @notificationServiceHelper.getUserById(target.userId)}")
    UserResponse getUser();

    @Value("#{target.tweetId == null ? null : @notificationServiceHelper.getTweetById(target.tweetId)}")
    TweetResponse getTweet();
}
