package com.gmail.javacoded78.service.util;

import com.gmail.javacoded78.dto.response.notification.NotificationListResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationTweetResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.feign.ListsClient;
import com.gmail.javacoded78.feign.TweetClient;
import com.gmail.javacoded78.feign.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationServiceHelper {

    private final UserClient userClient;
    private final TweetClient tweetClient;
    private final ListsClient listsClient;

    public UserResponse getUserById(Long userId) {
        return userClient.getUserById(userId);
    }

    public TweetResponse getTweetById(Long tweetId) {
        return tweetClient.getTweetById(tweetId);
    }

    public NotificationUserResponse getNotificationUser(Long userId) {
        return userClient.getNotificationUser(userId);
    }

    public NotificationTweetResponse getNotificationTweet(Long userId) {
        return tweetClient.getNotificationTweet(userId);
    }

    public NotificationListResponse getNotificationList(Long listId) {
        return listsClient.getNotificationList(listId);
    }
}
