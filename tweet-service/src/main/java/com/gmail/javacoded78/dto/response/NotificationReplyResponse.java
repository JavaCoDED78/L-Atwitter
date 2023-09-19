package com.gmail.javacoded78.dto.response;

import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationReplyResponse {

    private Long tweetId;
    private NotificationType notificationType;
    private TweetResponse tweet;
}