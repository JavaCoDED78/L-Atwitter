package com.gmail.javacoded78.dto.response.notification;

import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationReplyResponse {

    private Long tweetId;
    private NotificationType notificationType;
    private TweetResponse tweet;
}
