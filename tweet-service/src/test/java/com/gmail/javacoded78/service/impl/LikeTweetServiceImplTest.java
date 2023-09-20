package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.NotificationClient;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.LikeTweet;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.LikeTweetRepository;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.LikeTweetProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.service.TweetServiceTestHelper;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_DELETED;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class LikeTweetServiceImplTest {

    private final LikeTweetServiceImpl likeTweetService;

    @MockBean
    private final LikeTweetRepository likeTweetRepository;

    @MockBean
    private final TweetRepository tweetRepository;

    @MockBean
    private final NotificationClient notificationClient;

    @MockBean
    private final UserClient userClient;

    private static final ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    private static final PageRequest pageable = PageRequest.of(0, 20);
    private static Tweet tweet;

    @BeforeEach
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
        tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
    }

    @Test
    void getUserLikedTweets() {
        Page<LikeTweetProjection> likeTweet = new PageImpl<>(createMockLikeTweetProjectionList(), pageable, 20);
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        when(likeTweetRepository.getUserLikedTweets(TestConstants.USER_ID, pageable)).thenReturn(likeTweet);
        assertEquals(likeTweet, likeTweetService.getUserLikedTweets(TestConstants.USER_ID, pageable));
        verify(userClient, times(1)).isUserExists(TestConstants.USER_ID);
        verify(likeTweetRepository, times(1)).getUserLikedTweets(TestConstants.USER_ID, pageable);
    }

    @Test
    void getUserLikedTweets_ShouldUserIdNotFound() {
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> likeTweetService.getUserLikedTweets(TestConstants.USER_ID, pageable));
        assertEquals(String.format(USER_ID_NOT_FOUND, TestConstants.USER_ID), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getUserLikedTweets_ShouldUserNotFound() {
        tweet.setAuthorId(1L);
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> likeTweetService.getUserLikedTweets(1L, pageable));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getUserLikedTweets_ShouldUserProfileBLocked() {
        tweet.setAuthorId(1L);
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> likeTweetService.getUserLikedTweets(1L, pageable));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getLikedUsersByTweetId() {
        List<Long> usersIds = List.of(1L, 2L, 3L);
        HeaderResponse<UserResponse> headerResponse = new HeaderResponse<>(
                List.of(new UserResponse(), new UserResponse()), new HttpHeaders());
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(likeTweetRepository.getLikedUserIds(TestConstants.TWEET_ID)).thenReturn(usersIds);
        when(userClient.getUsersByIds(new IdsRequest(usersIds), pageable)).thenReturn(headerResponse);
        assertEquals(headerResponse, likeTweetService.getLikedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(likeTweetRepository, times(1)).getLikedUserIds(TestConstants.TWEET_ID);
        verify(userClient, times(1)).getUsersByIds(new IdsRequest(usersIds), pageable);
    }

    @Test
    void getLikedUsersByTweetId_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> likeTweetService.getLikedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getLikedUsersByTweetId_ShouldTweetDeleted() {
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> likeTweetService.getLikedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getLikedUsersByTweetId_ShouldUserNotFound() {
        tweet.setAuthorId(1L);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> likeTweetService.getLikedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getLikedUsersByTweetId_ShouldUserProfileBlocked() {
        tweet.setAuthorId(1L);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> likeTweetService.getLikedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void likeTweet_ShouldUnlikeTweet() {
        NotificationRequest request = TweetServiceTestHelper.createMockNotificationRequest(NotificationType.LIKE, false);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(likeTweetRepository.getLikedTweet(TestConstants.USER_ID, TestConstants.TWEET_ID)).thenReturn(new LikeTweet());
        when(notificationClient.sendTweetNotification(request)).thenReturn(new NotificationResponse());
        assertEquals(new NotificationResponse(), likeTweetService.likeTweet(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(likeTweetRepository, times(1)).getLikedTweet(TestConstants.USER_ID, TestConstants.TWEET_ID);
        verify(likeTweetRepository, times(1)).delete(new LikeTweet());
        verify(userClient, times(1)).updateLikeCount(false);
        verify(notificationClient, times(1)).sendTweetNotification(request);
    }

    @Test
    void likeTweet_ShouldLikeTweet() {
        NotificationRequest request = TweetServiceTestHelper.createMockNotificationRequest(NotificationType.LIKE, true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(likeTweetRepository.getLikedTweet(TestConstants.USER_ID, TestConstants.TWEET_ID)).thenReturn(null);
        when(notificationClient.sendTweetNotification(request)).thenReturn(new NotificationResponse());
        assertEquals(new NotificationResponse(), likeTweetService.likeTweet(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(likeTweetRepository, times(1)).getLikedTweet(TestConstants.USER_ID, TestConstants.TWEET_ID);
        verify(likeTweetRepository, times(1)).save(LikeTweet.builder()
                .userId(TestConstants.USER_ID)
                .tweetId(TestConstants.TWEET_ID)
                .build());
        verify(userClient, times(1)).updateLikeCount(true);
        verify(notificationClient, times(1)).sendTweetNotification(request);
    }

    @Test
    void likeTweet_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> likeTweetService.likeTweet(TestConstants.TWEET_ID));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void likeTweet_ShouldTweetDeleted() {
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> likeTweetService.likeTweet(TestConstants.TWEET_ID));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void likeTweet_ShouldUserNotFound() {
        tweet.setAuthorId(1L);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> likeTweetService.likeTweet(TestConstants.TWEET_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void likeTweet_ShouldUserProfileBlocked() {
        tweet.setAuthorId(1L);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> likeTweetService.likeTweet(TestConstants.TWEET_ID));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    private static List<LikeTweetProjection> createMockLikeTweetProjectionList() {
        LikeTweetProjection likeTweetProjection1 = factory.createProjection(
                LikeTweetProjection.class,
                Map.of(
                        "id", 1L,
                        "likeTweetDate", LocalDateTime.now(),
                        "tweetId", TestConstants.TWEET_ID,
                        "tweet", TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class)
                ));
        LikeTweetProjection likeTweetProjection2 = factory.createProjection(
                LikeTweetProjection.class,
                Map.of(
                        "id", 2L,
                        "likeTweetDate", LocalDateTime.now(),
                        "tweetId", TestConstants.TWEET_ID,
                        "tweet", TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class)
                ));
        return Arrays.asList(likeTweetProjection1, likeTweetProjection2);
    }
}
