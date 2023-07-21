package com.gmail.javacoded78.latwitter.repository.projection.notification;

import com.gmail.javacoded78.latwitter.enums.NotificationType;
import com.gmail.javacoded78.latwitter.repository.projection.ImageProjection;

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

        NotificationListProjection getList();
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

    interface NotificationListProjection {

        Long getId();

        String getName();
    }
}
