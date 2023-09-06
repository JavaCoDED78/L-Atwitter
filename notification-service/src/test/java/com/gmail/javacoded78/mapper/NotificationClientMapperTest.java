package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.model.Notification;
import com.gmail.javacoded78.service.NotificationClientService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class NotificationClientMapperTest {

    @Mock
    private final BasicMapper basicMapper;

    @Mock
    private final NotificationClientService notificationClientService;

    @InjectMocks
    private NotificationClientMapper notificationClientMapper;

    @Test
    void sendNotification() {
        NotificationRequest notificationRequest = getMockNotificationRequest();
        Notification notification = getMockNotification();
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .id(1L)
                .notificationType(NotificationType.LIKE)
                .build();
        when(basicMapper.convertToResponse(notificationRequest, Notification.class)).thenReturn(notification);
        when(notificationClientService.sendNotification(notification, true)).thenReturn(notificationResponse);
        assertNotNull(notificationClientMapper.sendNotification(notificationRequest));
        verify(basicMapper, times(1)).convertToResponse(notificationRequest, Notification.class);
        verify(notificationClientService, times(1)).sendNotification(notification, true);
    }

    @Test
    void sendTweetMentionNotification() {
        NotificationRequest notificationRequest = getMockNotificationRequest();
        Notification notification = getMockNotification();
        when(basicMapper.convertToResponse(notificationRequest, Notification.class)).thenReturn(notification);
        notificationClientMapper.sendTweetMentionNotification(notificationRequest);
        verify(basicMapper, times(1)).convertToResponse(notificationRequest, Notification.class);
        verify(notificationClientService, times(1)).sendTweetMentionNotification(notification);
    }

    @Test
    void sendTweetNotificationToSubscribers() {
        notificationClientMapper.sendTweetNotificationToSubscribers(11L);
        verify(notificationClientService, times(1)).sendTweetNotificationToSubscribers(11L);
    }

    private NotificationRequest getMockNotificationRequest() {
        return NotificationRequest.builder()
                .notificationType(NotificationType.LIKE)
                .notificationCondition(true)
                .notifiedUserId(1L)
                .userId(USER_ID)
                .tweetId(45L)
                .build();
    }

    private Notification getMockNotification() {
        return Notification.builder()
                .notificationType(NotificationType.LIKE)
                .notifiedUserId(1L)
                .userId(USER_ID)
                .tweetId(45L)
                .build();
    }
}