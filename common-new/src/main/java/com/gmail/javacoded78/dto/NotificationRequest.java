package com.gmail.javacoded78.dto;

import com.gmail.javacoded78.enums.NotificationType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class NotificationRequest {

    private NotificationType notificationType;
    private Long notifiedUserId;
    private Long userId;
    private Long userToFollowId;
    private Long tweetId;
    private Long listId;
    private boolean notificationCondition;

    public NotificationRequest(NotificationType notificationType, boolean isTweetLiked, Long notifiedUserId,
                               Long userId, Long tweetId) {

        this.notificationType = notificationType;
        this.notificationCondition = isTweetLiked;
        this.notifiedUserId = notifiedUserId;
        this.userId = userId;
        this.tweetId = tweetId;
    }

    public NotificationRequest(boolean isAddedToList, Long notifiedUserId, Long userId, Long listId) {

        this.notificationType = NotificationType.LISTS;
        this.notificationCondition = isAddedToList;
        this.notifiedUserId = notifiedUserId;
        this.userId = userId;
        this.listId = listId;
    }
}
