package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.NotificationTestHelper;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.TweetClient;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.repository.NotificationRepository;
import com.gmail.javacoded78.repository.projection.NotificationProjection;
import com.gmail.javacoded78.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.NOTIFICATION_NOT_FOUND;
import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class NotificationServiceImplTest {

    @Mock
    private final NotificationRepository notificationRepository;

    @Mock
    private final UserClient userClient;

    @Mock
    private final TweetClient tweetClient;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private final Pageable pageable = PageRequest.of(0, 20);
    private final List<Long> tweetIds = Arrays.asList(1L, 2L, 3L);

    @BeforeEach
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    void getUserNotifications() {
        Page<NotificationProjection> notifications = new PageImpl<>(
                NotificationTestHelper.getMockNotificationProjectionList(), pageable, 20);
        when(notificationRepository.getNotificationsByUserId(USER_ID, pageable)).thenReturn(notifications);
        Page<NotificationProjection> userNotifications = notificationService.getUserNotifications(pageable);
        assertThat(userNotifications.getContent()).hasSize(2);
        verify(userClient, times(1)).resetNotificationCount();
        verify(notificationRepository, times(1)).getNotificationsByUserId(USER_ID, pageable);
    }

    @Test
    void getUserMentionsNotifications() {
        when(notificationRepository.getTweetNotificationMentionIds(USER_ID, pageable))
                .thenReturn(new PageImpl<>(tweetIds, pageable, 20));
        when(tweetClient.getTweetsByIds(new IdsRequest(tweetIds)))
                .thenReturn(Arrays.asList(new TweetResponse(), new TweetResponse(), new TweetResponse()));
        Page<TweetResponse> userMentionsNotifications = notificationService.getUserMentionsNotifications(pageable);
        assertThat( userMentionsNotifications.getContent()).hasSize(3);
        verify(userClient, times(1)).resetMentionCount();
        verify(notificationRepository, times(1)).getTweetNotificationMentionIds(USER_ID, pageable);
        verify(tweetClient, times(1)).getTweetsByIds(new IdsRequest(tweetIds));
    }

    @Test
    void getTweetAuthorsNotifications() {
        when(userClient.getUsersWhichUserSubscribed())
                .thenReturn(Arrays.asList(new NotificationUserResponse(), new NotificationUserResponse()));
        List<NotificationUserResponse> notifications = notificationService.getTweetAuthorsNotifications();
        assertThat(notifications).hasSize(2);
        verify(userClient, times(1)).resetNotificationCount();
        verify(userClient, times(1)).getUsersWhichUserSubscribed();
    }

    @Test
    void getUserNotificationById() {
        when(notificationRepository.getUserNotificationById(USER_ID, 1L))
                .thenReturn(Optional.of(NotificationTestHelper.getMockNotificationInfoProjection()));
        assertNotNull(notificationService.getUserNotificationById(1L));
        verify(notificationRepository, times(1)).getUserNotificationById(USER_ID, 1L);
    }

    @Test
    void getUserNotificationById_shouldReturnNotificationNotFound() {
        when(notificationRepository.getUserNotificationById(USER_ID, 1L)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> notificationService.getUserNotificationById(1L));
        assertEquals(NOTIFICATION_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(notificationRepository, times(1)).getUserNotificationById(USER_ID, 1L);
    }

    @Test
    void getNotificationsFromTweetAuthors() {
        List<Long> userIds = Arrays.asList(4L, 5L, 6L);
        when(userClient.getUserIdsWhichUserSubscribed()).thenReturn(userIds);
        when(notificationRepository.getTweetIdsByNotificationType(userIds, USER_ID, pageable))
                .thenReturn(new PageImpl<>(tweetIds, pageable, 20));
        when(tweetClient.getTweetsByIds(new IdsRequest(tweetIds)))
                .thenReturn(Arrays.asList(new TweetResponse(), new TweetResponse(), new TweetResponse()));
        Page<TweetResponse> userMentionsNotifications = notificationService.getNotificationsFromTweetAuthors(pageable);
        assertThat(userMentionsNotifications.getContent()).hasSize(3);
        verify(userClient, times(1)).getUserIdsWhichUserSubscribed();
        verify(notificationRepository, times(1)).getTweetIdsByNotificationType(userIds, USER_ID, pageable);
        verify(tweetClient, times(1)).getTweetsByIds(new IdsRequest(tweetIds));
    }
}