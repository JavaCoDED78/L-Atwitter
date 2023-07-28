package com.gmail.javacoded78.projection;

import com.gmail.javacoded78.enums.NotificationType;

import java.time.LocalDateTime;

public interface NotificationInfoProjection {

    Long getId();
    LocalDateTime getDate();
    NotificationType getNotificationType();
    UserProjection getUser();
    TweetProjection getTweet();
}
