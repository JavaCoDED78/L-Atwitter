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
import com.gmail.javacoded78.service.NotificationClientService;
import com.gmail.javacoded78.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NotificationClientServiceImplTest {

    @Autowired
    private NotificationClientService notificationClientService;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private UserClient userClient;

    @MockBean
    private ListsClient listsClient;

    @MockBean
    private TweetClient tweetClient;

    @MockBean
    private BasicMapper basicMapper;

    @Before
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    public void sendNotification_shouldReturnExistedListNotification() {
        Notification notification = mockListNotification(true);
        assertNotNull(notificationClientService.sendNotification(notification, true));
        verify(notificationRepository, times(1))
                .isListNotificationExists(notification.getNotifiedUserId(), notification.getListId(), USER_ID, notification.getNotificationType());
        verify(userClient, times(1)).getNotificationUser(USER_ID);
        verify(listsClient, times(1)).getNotificationList(2L);
    }

    @Test
    public void sendNotification_shouldSaveAndReturnListNotification() {
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
    public void sendNotification_shouldReturnExistedUserNotification() {
        Notification notification = mockUserNotification(true);
        assertNotNull(notificationClientService.sendNotification(notification, true));
        verify(notificationRepository, times(1))
                .isUserNotificationExists(notification.getNotifiedUserId(), notification.getUserToFollowId(), USER_ID, notification.getNotificationType());
        verify(userClient, times(2)).getNotificationUser(any());
    }

    @Test
    public void sendNotification_shouldSaveAndReturnUserNotification() {
        Notification notification = mockUserNotification(false);
        assertNotNull(notificationClientService.sendNotification(notification, true));
        verify(notificationRepository, times(1))
                .isUserNotificationExists(notification.getNotifiedUserId(), notification.getUserToFollowId(), USER_ID, notification.getNotificationType());
        verify(notificationRepository, times(1)).save(notification);
        verify(userClient, times(1)).increaseNotificationsCount(notification.getNotifiedUserId());
        verify(userClient, times(2)).getNotificationUser(any());
    }

    @Test
    public void sendNotification_shouldReturnExistedTweetNotification() {
        Notification notification = mockTweetNotification(true);
        assertNotNull(notificationClientService.sendNotification(notification, true));
        verify(notificationRepository, times(1))
                .isTweetNotificationExists(notification.getNotifiedUserId(), notification.getTweetId(), USER_ID, notification.getNotificationType());
        verify(userClient, times(1)).getNotificationUser(notification.getUserId());
        verify(tweetClient, times(1)).getNotificationTweet(notification.getTweetId());
    }

    @Test
    public void sendNotification_shouldSaveAndReturnTweetNotification() {
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
    public void sendTweetMentionNotification() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setDate(LocalDateTime.now());
        notification.setNotificationType(NotificationType.LIKE);
        notification.setNotifiedUserId(2L);
        notification.setTweetId(3L);
        notification.setUserId(USER_ID);
        notificationClientService.sendTweetMentionNotification(notification);
        verify(notificationRepository, times(1)).save(notification);
        verify(userClient, times(1)).increaseMentionsCount(notification.getNotifiedUserId());
    }

    @Test
    public void sendTweetNotificationToSubscribers() {
        when(userClient.getSubscribersByUserId(USER_ID)).thenReturn(Arrays.asList(6L, 7L, 8L));
        notificationClientService.sendTweetNotificationToSubscribers(11L);
        verify(userClient, times(1)).getSubscribersByUserId(USER_ID);
        verify(notificationRepository, times(3)).save(any());
        verify(userClient, times(3)).increaseNotificationsCount(any());
    }

    private Notification mockListNotification(boolean isNotificationExists) {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setDate(LocalDateTime.now());
        notification.setNotificationType(NotificationType.LISTS);
        notification.setNotifiedUserId(1L);
        notification.setListId(2L);
        notification.setUserId(USER_ID);
        when(notificationRepository
                .isListNotificationExists(notification.getNotifiedUserId(), notification.getListId(), USER_ID, notification.getNotificationType()))
                .thenReturn(isNotificationExists);
        when(userClient.getNotificationUser(notification.getUserId())).thenReturn(new NotificationUserResponse());
        when(listsClient.getNotificationList(notification.getListId())).thenReturn(new NotificationListResponse());
        when(basicMapper.convertToResponse(notification, NotificationResponse.class))
                .thenReturn(modelMapper.map(notification, NotificationResponse.class));
        return notification;
    }

    private Notification mockUserNotification(boolean isNotificationExists) {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setDate(LocalDateTime.now());
        notification.setNotificationType(NotificationType.FOLLOW);
        notification.setNotifiedUserId(1L);
        notification.setUserToFollowId(2L);
        notification.setUserId(USER_ID);
        when(notificationRepository
                .isUserNotificationExists(notification.getNotifiedUserId(), notification.getUserToFollowId(), USER_ID, notification.getNotificationType()))
                .thenReturn(isNotificationExists);
        when(userClient.getNotificationUser(notification.getUserId())).thenReturn(new NotificationUserResponse());
        when(userClient.getNotificationUser(notification.getUserToFollowId())).thenReturn(new NotificationUserResponse());
        when(basicMapper.convertToResponse(notification, NotificationResponse.class))
                .thenReturn(modelMapper.map(notification, NotificationResponse.class));
        return notification;
    }

    private Notification mockTweetNotification(boolean isNotificationExists) {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setDate(LocalDateTime.now());
        notification.setNotificationType(NotificationType.LIKE);
        notification.setNotifiedUserId(1L);
        notification.setTweetId(3L);
        notification.setUserId(USER_ID);
        when(notificationRepository
                .isTweetNotificationExists(notification.getNotifiedUserId(), notification.getTweetId(), USER_ID, notification.getNotificationType()))
                .thenReturn(isNotificationExists);
        when(userClient.getNotificationUser(notification.getUserId())).thenReturn(new NotificationUserResponse());
        when(tweetClient.getNotificationTweet(notification.getTweetId())).thenReturn(new NotificationTweetResponse());
        when(basicMapper.convertToResponse(notification, NotificationResponse.class))
                .thenReturn(modelMapper.map(notification, NotificationResponse.class));
        return notification;
    }
}