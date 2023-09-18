package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.Retweet;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.RetweetRepository;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.RetweetProjection;
import com.gmail.javacoded78.repository.projection.TweetUserProjection;
import com.gmail.javacoded78.service.TweetServiceTestHelper;
import com.gmail.javacoded78.service.util.TweetServiceHelper;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

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
@RunWith(SpringRunner.class)
public class RetweetServiceImplTest {

    @Autowired
    private RetweetServiceImpl retweetService;

    @MockBean
    private TweetRepository tweetRepository;

    @MockBean
    private TweetServiceHelper tweetServiceHelper;

    @MockBean
    private RetweetRepository retweetRepository;

    @MockBean
    private UserClient userClient;

    private static final ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    private static final PageRequest pageable = PageRequest.of(0, 20);
    private static Tweet tweet;

    @Before
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
        tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
    }

    @Test
    public void getUserRetweetsAndReplies() {
        List<TweetUserProjection> tweetUserProjections = TweetServiceTestHelper.createMockTweetUserProjectionList();
        List<RetweetProjection> retweetProjections = TweetServiceTestHelper.createMockRetweetProjectionList();
        Page<TweetUserProjection> pageableTweetUserProjections = new PageImpl<>(tweetUserProjections, pageable, 20);
        int totalPages = tweetUserProjections.size() + retweetProjections.size();
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(false);
        when(tweetRepository.getRepliesByUserId(1L)).thenReturn(tweetUserProjections);
        when(retweetRepository.getRetweetsByUserId(1L)).thenReturn(retweetProjections);
        when(tweetServiceHelper.combineTweetsArrays(tweetUserProjections, retweetProjections))
                .thenReturn(tweetUserProjections);
        when(tweetServiceHelper.getPageableTweetProjectionList(pageable, tweetUserProjections, totalPages))
                .thenReturn(pageableTweetUserProjections);
        assertEquals(pageableTweetUserProjections, retweetService.getUserRetweetsAndReplies(1L, pageable));
        verify(userClient, times(1)).isUserExists(1L);
        verify(userClient, times(1)).isUserHavePrivateProfile(1L);
        verify(userClient, times(1)).isMyProfileBlockedByUser(1L);
        verify(tweetRepository, times(1)).getRepliesByUserId(1L);
        verify(retweetRepository, times(1)).getRetweetsByUserId(1L);
        verify(tweetServiceHelper, times(1)).combineTweetsArrays(tweetUserProjections, retweetProjections);
        verify(tweetServiceHelper, times(1)).getPageableTweetProjectionList(pageable, tweetUserProjections, totalPages);
    }

    @Test
    public void getUserRetweetsAndReplies_ShouldUserIdNotFound() {
        when(userClient.isUserExists(1L)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> retweetService.getUserRetweetsAndReplies(1L, pageable));
        assertEquals(String.format(USER_ID_NOT_FOUND, 1L), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void getUserRetweetsAndReplies_ShouldUserNotFound() {
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> retweetService.getUserRetweetsAndReplies(1L, pageable));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void getUserRetweetsAndReplies_ShouldUserProfileBlocked() {
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> retweetService.getUserRetweetsAndReplies(1L, pageable));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void getRetweetedUsersByTweetId() {
        List<Long> retweetedUserIds = List.of(1L, 2L, 3L);
        HeaderResponse<UserResponse> headerResponse = new HeaderResponse<>(
                List.of(new UserResponse(), new UserResponse()), new HttpHeaders());
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(retweetRepository.getRetweetedUserIds(TestConstants.TWEET_ID)).thenReturn(retweetedUserIds);
        when(userClient.getUsersByIds(new IdsRequest(retweetedUserIds), pageable)).thenReturn(headerResponse);
        assertEquals(headerResponse, retweetService.getRetweetedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(retweetRepository, times(1)).getRetweetedUserIds(TestConstants.TWEET_ID);
        verify(userClient, times(1)).getUsersByIds(new IdsRequest(retweetedUserIds), pageable);
    }

    @Test
    public void getRetweetedUsersByTweetId_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> retweetService.getRetweetedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void getRetweetedUsersByTweetId_ShouldTweetDeleted() {
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> retweetService.getRetweetedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void getRetweetedUsersByTweetId_ShouldUserNotFound() {
        tweet.setAuthorId(1L);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> retweetService.getRetweetedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void getRetweetedUsersByTweetId_ShouldUserProfileBlocked() {
        tweet.setAuthorId(1L);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> retweetService.getRetweetedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void retweet_ShouldUnRetweet() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(retweetRepository.isTweetRetweeted(TestConstants.USER_ID, TestConstants.TWEET_ID)).thenReturn(new Retweet());
        when(tweetServiceHelper
                .sendNotification(NotificationType.RETWEET, false, TestConstants.USER_ID, TestConstants.USER_ID, TestConstants.TWEET_ID))
                .thenReturn(new NotificationResponse());
        assertEquals(new NotificationResponse(), retweetService.retweet(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(retweetRepository, times(1)).isTweetRetweeted(TestConstants.USER_ID, TestConstants.TWEET_ID);
        verify(retweetRepository, times(1)).delete(new Retweet());
        verify(userClient, times(1)).updateTweetCount(false);
        verify(tweetServiceHelper, times(1))
                .sendNotification(NotificationType.RETWEET, false, TestConstants.USER_ID, TestConstants.USER_ID, TestConstants.TWEET_ID);
    }

    @Test
    public void retweet_ShouldRetweet() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(retweetRepository.isTweetRetweeted(TestConstants.USER_ID, TestConstants.TWEET_ID)).thenReturn(null);
        when(tweetServiceHelper
                .sendNotification(NotificationType.RETWEET, true, TestConstants.USER_ID, TestConstants.USER_ID, TestConstants.TWEET_ID))
                .thenReturn(new NotificationResponse());
        assertEquals(new NotificationResponse(), retweetService.retweet(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(retweetRepository, times(1)).isTweetRetweeted(TestConstants.USER_ID, TestConstants.TWEET_ID);
        verify(retweetRepository, times(1)).save(new Retweet(TestConstants.USER_ID, TestConstants.TWEET_ID));
        verify(userClient, times(1)).updateTweetCount(true);
        verify(tweetServiceHelper, times(1))
                .sendNotification(NotificationType.RETWEET, true, TestConstants.USER_ID, TestConstants.USER_ID, TestConstants.TWEET_ID);
    }

    @Test
    public void retweet_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> retweetService.retweet(TestConstants.TWEET_ID));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void retweet_ShouldTweetDeleted() {
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> retweetService.retweet(TestConstants.TWEET_ID));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void retweet_ShouldUserNotFound() {
        tweet.setAuthorId(1L);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> retweetService.retweet(TestConstants.TWEET_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void retweet_ShouldUserProfileBlocked() {
        tweet.setAuthorId(1L);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> retweetService.retweet(TestConstants.TWEET_ID));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}