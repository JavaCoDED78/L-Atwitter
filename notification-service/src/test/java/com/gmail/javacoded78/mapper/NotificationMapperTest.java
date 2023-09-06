package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.NotificationTestHelper;
import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.NotificationInfoResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationListResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationTweetResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.repository.projection.NotificationInfoProjection;
import com.gmail.javacoded78.repository.projection.NotificationProjection;
import com.gmail.javacoded78.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class NotificationMapperTest {

    @Mock
    private final BasicMapper basicMapper;

    @Mock
    private final NotificationService notificationService;

    private final Pageable pageable = PageRequest.of(0, 20);

    @InjectMocks
    private NotificationMapper notificationMapper;

    @Test
    void getUserNotifications() {
        Page<NotificationProjection> notifications = new PageImpl<>(
                NotificationTestHelper.getMockNotificationProjectionList(), pageable, 20);
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .id(1L)
                .date(LocalDateTime.now())
                .notificationType(NotificationType.TWEET)
                .user(new NotificationUserResponse())
                .notifiedUserId(1L)
                .userToFollow(new NotificationUserResponse())
                .tweet(new NotificationTweetResponse())
                .list(new NotificationListResponse())
                .build();
        HeaderResponse<NotificationResponse> headerResponse = new HeaderResponse<>(
                Arrays.asList(notificationResponse, notificationResponse), new HttpHeaders());
        when(notificationService.getUserNotifications(pageable)).thenReturn(notifications);
        when(basicMapper.getHeaderResponse(notifications, NotificationResponse.class)).thenReturn(headerResponse);
        HeaderResponse<NotificationResponse> userNotifications = notificationMapper.getUserNotifications(pageable);
        assertThat(userNotifications.getItems()).hasSize(2);
        verify(notificationService, times(1)).getUserNotifications(pageable);
        verify(basicMapper, times(1)).getHeaderResponse(notifications, NotificationResponse.class);
    }

    @Test
    void getUserMentionsNotifications() {
        Page<TweetResponse> tweets = new PageImpl<>(Arrays.asList(new TweetResponse(), new TweetResponse()), pageable, 20);
        HeaderResponse<TweetResponse> headerResponse = new HeaderResponse<>(tweets.getContent(), new HttpHeaders());
        when(notificationService.getUserMentionsNotifications(pageable)).thenReturn(tweets);
        when(basicMapper.getHeaderResponse(tweets, TweetResponse.class)).thenReturn(headerResponse);
        HeaderResponse<TweetResponse> userNotifications = notificationMapper.getUserMentionsNotifications(pageable);
        assertThat(userNotifications.getItems()).hasSize(2);
        verify(notificationService, times(1)).getUserMentionsNotifications(pageable);
        verify(basicMapper, times(1)).getHeaderResponse(tweets, TweetResponse.class);
    }

    @Test
    void getTweetAuthorsNotifications() {
        when(notificationService.getTweetAuthorsNotifications())
                .thenReturn(Arrays.asList(new NotificationUserResponse(), new NotificationUserResponse()));
        assertThat(notificationMapper.getTweetAuthorsNotifications()).hasSize(2);
        verify(notificationService, times(1)).getTweetAuthorsNotifications();
    }

    @Test
    void getUserNotificationById() {
        NotificationInfoProjection infoProjection = NotificationTestHelper.getMockNotificationInfoProjection();
        when(notificationService.getUserNotificationById(1L)).thenReturn(infoProjection);
        when(basicMapper.convertToResponse(infoProjection, NotificationInfoResponse.class)).thenReturn(new NotificationInfoResponse());
        assertNotNull(notificationMapper.getUserNotificationById(1L));
        verify(notificationService, times(1)).getUserNotificationById(1L);
        verify(basicMapper, times(1)).convertToResponse(infoProjection, NotificationInfoResponse.class);
    }

    @Test
    void getNotificationsFromTweetAuthors() {
        Page<TweetResponse> tweets = new PageImpl<>(Arrays.asList(new TweetResponse(), new TweetResponse()), pageable, 20);
        HeaderResponse<TweetResponse> headerResponse = new HeaderResponse<>(tweets.getContent(), new HttpHeaders());
        when(notificationService.getNotificationsFromTweetAuthors(pageable)).thenReturn(tweets);
        when(basicMapper.getHeaderResponse(tweets, TweetResponse.class)).thenReturn(headerResponse);
        HeaderResponse<TweetResponse> userNotifications = notificationMapper.getNotificationsFromTweetAuthors(pageable);
        assertThat(userNotifications.getItems()).hasSize(2);
        verify(notificationService, times(1)).getNotificationsFromTweetAuthors(pageable);
        verify(basicMapper, times(1)).getHeaderResponse(tweets, TweetResponse.class);
    }
}