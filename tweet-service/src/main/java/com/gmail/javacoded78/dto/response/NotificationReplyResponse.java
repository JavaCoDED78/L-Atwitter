package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.common.dto.TweetResponse;
import com.gmail.javacoded78.common.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationReplyResponse {

    private Long tweetId;
    private NotificationType notificationType;
    private TweetResponse tweet;
}
