package com.gmail.javacoded78.service.util;

import com.gmail.javacoded78.constants.PathConstants;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_TWEET_TEXT_LENGTH;
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
public class TweetValidationHelperTest {

    @Autowired
    private TweetValidationHelper tweetValidationHelper;

    @MockBean
    private TweetRepository tweetRepository;

    @MockBean
    private UserClient userClient;

    @Before
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    public void getValidUserIds() {
        List<Long> tweetAuthorIds = List.of(1L, 2L, 3L);
        when(tweetRepository.getTweetAuthorIds()).thenReturn(tweetAuthorIds);
        when(userClient.getValidUserIds(new IdsRequest(tweetAuthorIds))).thenReturn(tweetAuthorIds);
        assertEquals(tweetAuthorIds, tweetValidationHelper.getValidUserIds());
        verify(tweetRepository, times(1)).getTweetAuthorIds();
        verify(userClient, times(1)).getValidUserIds(new IdsRequest(tweetAuthorIds));
    }

    @Test
    public void checkValidTweet() {
        Tweet tweet = new Tweet();
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        assertEquals(tweet, tweetValidationHelper.checkValidTweet(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
    }

    @Test
    public void checkValidTweet_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetValidationHelper.checkValidTweet(TestConstants.TWEET_ID));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void checkValidTweet_ShouldTweetDeleted() {
        Tweet tweet = new Tweet();
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetValidationHelper.checkValidTweet(TestConstants.TWEET_ID));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void checkValidTweet_ShouldUserNotFound() {
        mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetValidationHelper.checkValidTweet(TestConstants.TWEET_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void checkValidTweet_ShouldUserProfileBlocked() {
        mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetValidationHelper.checkValidTweet(TestConstants.TWEET_ID));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void validateUserProfile_ShouldUserNotFound() {
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetValidationHelper.validateUserProfile(TestConstants.USER_ID));
        assertEquals(String.format(USER_ID_NOT_FOUND, TestConstants.USER_ID), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void checkTweetTextLength() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetValidationHelper.checkTweetTextLength(""));
        assertEquals(INCORRECT_TWEET_TEXT_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    private void mockAuthenticatedUserId() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader(PathConstants.AUTH_USER_ID_HEADER, 1L);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
    }
}