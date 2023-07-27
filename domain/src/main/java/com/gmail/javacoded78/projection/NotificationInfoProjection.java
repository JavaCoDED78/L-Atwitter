package com.gmail.javacoded78.projection;

import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.repository.projection.tweet.TweetProjection;
import com.gmail.javacoded78.repository.projection.user.UserProjection;

import java.time.LocalDateTime;

public interface NotificationInfoProjection {

    Long getId();
    LocalDateTime getDate();
    NotificationType getNotificationType();
    UserProjection getUser();
    TweetProjection getTweet();
}
