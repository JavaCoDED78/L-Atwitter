package com.gmail.javacoded78.latwitter.repository.projection.notification;

import com.gmail.javacoded78.latwitter.enums.NotificationType;
import com.gmail.javacoded78.latwitter.repository.projection.tweet.TweetProjection;
import com.gmail.javacoded78.latwitter.repository.projection.user.UserProjection;

import java.time.LocalDateTime;

public interface NotificationInfoProjection {

    Long getId();

    LocalDateTime getDate();

    NotificationType getNotificationType();

    UserProjection getUser();

    TweetProjection getTweet();
}
