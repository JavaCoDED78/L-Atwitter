package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.response.notification.NotificationListResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationTweetResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.feign.ListsClient;
import com.gmail.javacoded78.feign.TweetClient;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.mapper.BasicMapper;
import com.gmail.javacoded78.model.Notification;
import com.gmail.javacoded78.repository.NotificationRepository;
import com.gmail.javacoded78.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class NotificationClientServiceImplTest {

    @Mock
    private final NotificationRepository notificationRepository;

    @Mock
    private final UserClient userClient;

    @Mock
    private final ListsClient listsClient;

    @Mock
    private final TweetClient tweetClient;

    @Mock
    private final BasicMapper basicMapper;

    @InjectMocks
    private NotificationClientServiceImpl notificationClientService;

    @BeforeEach
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    void sendNotification_shouldReturnExistedListNotification() {
        Notification notification = mockListNotification(true);
        assertNotNull(notificationClientService.sendNotification(notification, true));
        verify(notificationRepository, times(1))
                .isListNotificationExists(notification.getNotifiedUserId(), notification.getListId(), USER_ID, notification.getNotificationType());
        verify(userClient, times(1)).getNotificationUser(USER_ID);
        verify(listsClient, times(1)).getNotificationList(2L);
    }

    @Test
    void sendNotification_shouldSaveAndReturnListNotification() {
        Notification notification = mockListNotification(false);
        assertNotNull(notificationClientService.sendNotification(notification, true));
        verify(notificationRepository, times(1))
                .isListNotificationExists(notification.getNotifiedUserId(), notification.getListId(), USER_ID, notification.getNotificationType());
        verify(notificationRepository, times(1)).save(notification);
        verify(userClient, times(1)).increaseNotificationsCount(notification.getNotifiedUserId());
        verify(userClient, times(1)).getNotificationUser(USER_ID);
        verify(listsClient, times(1)).getNotificationList(2L);
    }

    @Test
    void sendNotification_shouldReturnExistedUserNotification() {
        Notification notification = mockUserNotification(true);
        assertNotNull(notificationClientService.sendNotification(notification, true));
        verify(notificationRepository, times(1))
                .isUserNotificationExists(notification.getNotifiedUserId(), notification.getUserToFollowId(), USER_ID, notification.getNotificationType());
        verify(userClient, times(2)).getNotificationUser(any());
    }

    @Test
    void sendNotification_shouldSaveAndReturnUserNotification() {
        Notification notification = mockUserNotification(false);
        assertNotNull(notificationClientService.sendNotification(notification, true));
        verify(notificationRepository, times(1))
                .isUserNotificationExists(notification.getNotifiedUserId(), notification.getUserToFollowId(), USER_ID, notification.getNotificationType());
        verify(notificationRepository, times(1)).save(notification);
        verify(userClient, times(1)).increaseNotificationsCount(notification.getNotifiedUserId());
        verify(userClient, times(2)).getNotificationUser(any());
    }

    @Test
    void sendNotification_shouldReturnExistedTweetNotification() {
        Notification notification = mockTweetNotification(true);
        assertNotNull(notificationClientService.sendNotification(notification, true));
        verify(notificationRepository, times(1))
                .isTweetNotificationExists(notification.getNotifiedUserId(), notification.getTweetId(), USER_ID, notification.getNotificationType());
        verify(userClient, times(1)).getNotificationUser(notification.getUserId());
        verify(tweetClient, times(1)).getNotificationTweet(notification.getTweetId());
    }

    @Test
    void sendNotification_shouldSaveAndReturnTweetNotification() {
        Notification notification = mockTweetNotification(false);
        assertNotNull(notificationClientService.sendNotification(notification, true));
        verify(notificationRepository, times(1))
                .isTweetNotificationExists(notification.getNotifiedUserId(), notification.getTweetId(), USER_ID, notification.getNotificationType());
        verify(notificationRepository, times(1)).save(notification);
        verify(userClient, times(1)).increaseNotificationsCount(notification.getNotifiedUserId());
        verify(userClient, times(1)).getNotificationUser(notification.getUserId());
        verify(tweetClient, times(1)).getNotificationTweet(notification.getTweetId());
    }

    @Test
    void sendTweetMentionNotification() {
        Notification notification = Notification.builder()
                .id(1L)
                .date(LocalDateTime.now())
                .notificationType(NotificationType.LIKE)
                .notifiedUserId(2L)
                .tweetId(3L)
                .userId(USER_ID)
                .build();
        notificationClientService.sendTweetMentionNotification(notification);
        verify(notificationRepository, times(1)).save(notification);
        verify(userClient, times(1)).increaseMentionsCount(notification.getNotifiedUserId());
    }

    @Test
    void sendTweetNotificationToSubscribers() {
        when(userClient.getSubscribersByUserId(USER_ID)).thenReturn(Arrays.asList(6L, 7L, 8L));
        notificationClientService.sendTweetNotificationToSubscribers(11L);
        verify(userClient, times(1)).getSubscribersByUserId(USER_ID);
        verify(notificationRepository, times(3)).save(any());
        verify(userClient, times(3)).increaseNotificationsCount(any());
    }

    private Notification mockListNotification(boolean isNotificationExists) {
        Notification notification = Notification.builder()
                .id(1L)
                .date(LocalDateTime.now())
                .notificationType(NotificationType.LISTS)
                .notifiedUserId(1L)
                .listId(2L)
                .userId(USER_ID)
                .build();
        when(notificationRepository
                .isListNotificationExists(notification.getNotifiedUserId(), notification.getListId(), USER_ID, notification.getNotificationType()))
                .thenReturn(isNotificationExists);
        when(userClient.getNotificationUser(notification.getUserId())).thenReturn(new NotificationUserResponse());
        when(listsClient.getNotificationList(notification.getListId())).thenReturn(new NotificationListResponse());
        when(basicMapper.convertToResponse(notification, NotificationResponse.class))
                .thenReturn(new NotificationResponse());
        return notification;
    }

    private Notification mockUserNotification(boolean isNotificationExists) {
        Notification notification = Notification.builder()
                .id(1L)
                .date(LocalDateTime.now())
                .notificationType(NotificationType.FOLLOW)
                .notifiedUserId(1L)
                .listId(2L)
                .userId(USER_ID)
                .build();
        when(notificationRepository
                .isUserNotificationExists(notification.getNotifiedUserId(), notification.getUserToFollowId(), USER_ID, notification.getNotificationType()))
                .thenReturn(isNotificationExists);
        when(userClient.getNotificationUser(notification.getUserId())).thenReturn(new NotificationUserResponse());
        when(userClient.getNotificationUser(notification.getUserToFollowId())).thenReturn(new NotificationUserResponse());
        when(basicMapper.convertToResponse(notification, NotificationResponse.class))
                .thenReturn(new NotificationResponse());
        return notification;
    }

    private Notification mockTweetNotification(boolean isNotificationExists) {
        Notification notification = Notification.builder()
                .id(1L)
                .date(LocalDateTime.now())
                .notificationType(NotificationType.LIKE)
                .notifiedUserId(1L)
                .listId(3L)
                .userId(USER_ID)
                .build();
        when(notificationRepository
                .isTweetNotificationExists(notification.getNotifiedUserId(), notification.getTweetId(), USER_ID, notification.getNotificationType()))
                .thenReturn(isNotificationExists);
        when(userClient.getNotificationUser(notification.getUserId())).thenReturn(new NotificationUserResponse());
        when(tweetClient.getNotificationTweet(notification.getTweetId())).thenReturn(new NotificationTweetResponse());
        when(basicMapper.convertToResponse(notification, NotificationResponse.class))
                .thenReturn(new NotificationResponse());
        return notification;
    }
}