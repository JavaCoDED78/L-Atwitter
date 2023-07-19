package com.gmail.javacoded78.latwitter.repository.projection;

import com.gmail.javacoded78.latwitter.model.NotificationType;

import java.time.LocalDateTime;

public interface NotificationProjection {

    Notification getNotification();

    interface Notification {

        Long getId();

        LocalDateTime getDate();

        NotificationType getNotificationType();

        NotificationUserProjection getUser();

        NotificationUserProjection getUserToFollow();

        NotificationTweetProjection getTweet();
    }

    interface NotificationUserProjection {

        Long getId();

        String getUsername();

        ImageProjection getAvatar();
    }

    interface NotificationTweetProjection {

        Long getId();

        String getText();

        NotificationUserProjection getUser();
    }
}
