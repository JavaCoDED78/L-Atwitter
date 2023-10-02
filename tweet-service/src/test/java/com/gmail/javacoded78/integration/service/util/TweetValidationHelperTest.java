package com.gmail.javacoded78.integration.service.util;

import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.integration.service.TweetServiceTestHelper;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.service.util.TweetValidationHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_TWEET_TEXT_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_DELETED;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
class TweetValidationHelperTest extends AbstractAuthTest {

    private final TweetValidationHelper tweetValidationHelper;

    @MockBean
    private final TweetRepository tweetRepository;

    @MockBean
    private final UserClient userClient;

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    void getValidUserIds() {
        when(tweetRepository.getTweetAuthorIds()).thenReturn(ids);
        when(userClient.getValidUserIds(new IdsRequest(ids))).thenReturn(ids);
        assertEquals(ids, tweetValidationHelper.getValidUserIds());
        verify(tweetRepository, times(1)).getTweetAuthorIds();
        verify(userClient, times(1)).getValidUserIds(new IdsRequest(ids));
    }

    @Test
    void checkValidTweet() {
        Tweet tweet = new Tweet();
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        assertEquals(tweet, tweetValidationHelper.checkValidTweet(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
    }

    @Test
    void checkValidTweet_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetValidationHelper.checkValidTweet(TestConstants.TWEET_ID));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void checkValidTweet_ShouldTweetDeleted() {
        Tweet tweet = new Tweet();
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetValidationHelper.checkValidTweet(TestConstants.TWEET_ID));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void checkValidTweet_ShouldUserNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
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
    void checkValidTweet_ShouldUserProfileBlocked() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
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
    void validateUserProfile_ShouldUserNotFound() {
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetValidationHelper.validateUserProfile(TestConstants.USER_ID));
        assertEquals(String.format(USER_ID_NOT_FOUND, TestConstants.USER_ID), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void checkTweetTextLength() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetValidationHelper.checkTweetTextLength(""));
        assertEquals(INCORRECT_TWEET_TEXT_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
