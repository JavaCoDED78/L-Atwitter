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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class NotificationServiceHelperTest {

    @Mock
    private final UserClient userClient;

    @Mock
    private final TweetClient tweetClient;

    @Mock
    private final ListsClient listsClient;

    @InjectMocks
    private NotificationServiceHelper notificationServiceHelper;

    @Test
    void getUserById() {
        when(userClient.getUserById(USER_ID)).thenReturn(new UserResponse());
        assertNotNull(notificationServiceHelper.getUserById(USER_ID));
        verify(userClient, times(1)).getUserById(USER_ID);
    }

    @Test
    void getTweetById() {
        when(tweetClient.getTweetById(11L)).thenReturn(new TweetResponse());
        assertNotNull(notificationServiceHelper.getTweetById(11L));
        verify(tweetClient, times(1)).getTweetById(11L);
    }

    @Test
    void getNotificationUser() {
        when(userClient.getNotificationUser(USER_ID)).thenReturn(new NotificationUserResponse());
        assertNotNull(notificationServiceHelper.getNotificationUser(USER_ID));
        verify(userClient, times(1)).getNotificationUser(USER_ID);
    }

    @Test
    void getNotificationTweet() {
        when(tweetClient.getNotificationTweet(USER_ID)).thenReturn(new NotificationTweetResponse());
        assertNotNull(notificationServiceHelper.getNotificationTweet(USER_ID));
        verify(tweetClient, times(1)).getNotificationTweet(USER_ID);
    }

    @Test
    void getNotificationList() {
        when(listsClient.getNotificationList(3L)).thenReturn(new NotificationListResponse());
        assertNotNull(notificationServiceHelper.getNotificationList(3L));
        verify(listsClient, times(1)).getNotificationList(3L);
    }
}