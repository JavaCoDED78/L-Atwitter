package com.gmail.javacoded78.integration.service.impl;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.TagClient;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.integration.service.TweetServiceTestHelper;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.RetweetRepository;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.ProfileTweetImageProjection;
import com.gmail.javacoded78.repository.projection.RetweetProjection;
import com.gmail.javacoded78.repository.projection.TweetAdditionalInfoProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.repository.projection.TweetUserProjection;
import com.gmail.javacoded78.service.impl.TweetServiceImpl;
import com.gmail.javacoded78.service.util.TweetServiceHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
class TweetServiceImplTest extends AbstractAuthTest {

    private final TweetServiceImpl tweetService;

    @MockBean
    private final TweetRepository tweetRepository;

    @MockBean
    private final TweetServiceHelper tweetServiceHelper;


    @MockBean
    private final RetweetRepository retweetRepository;

    @MockBean
    private final UserClient userClient;

    @MockBean
    private final TagClient tagClient;

    private static final List<TweetProjection> tweetProjections = Arrays.asList(
            TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class),
            TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class));
    private static final Page<TweetProjection> pageableTweetProjections = new PageImpl<>(tweetProjections, pageable, 20);

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    void getTweets() {
        when(tweetRepository.getTweetAuthorIds()).thenReturn(ids);
        when(userClient.getValidUserIds(new IdsRequest(ids))).thenReturn(ids);
        when(tweetRepository.getTweetsByAuthorIds(ids, pageable)).thenReturn(pageableTweetProjections);
        assertEquals(pageableTweetProjections, tweetService.getTweets(pageable));
        verify(tweetRepository, times(1)).getTweetsByAuthorIds(ids, pageable);
    }

    @Test
    void getTweetById() {
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetProjection.class)).thenReturn(Optional.of(tweetProjection));
        assertEquals(tweetProjection, tweetService.getTweetById(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).getTweetById(TestConstants.TWEET_ID, TweetProjection.class);
    }

    @Test
    void getTweetById_ShouldTweetNotFound() {
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetProjection.class)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTweetById(TestConstants.TWEET_ID));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getTweetById_ShouldTweetDeleted() {
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(true, TweetProjection.class);
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetProjection.class)).thenReturn(Optional.of(tweetProjection));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTweetById(TestConstants.TWEET_ID));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getTweetById_ShouldUserNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetProjection.class)).thenReturn(Optional.of(tweetProjection));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTweetById(TestConstants.TWEET_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getTweetById_ShouldUserProfileBlocked() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetProjection.class)).thenReturn(Optional.of(tweetProjection));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTweetById(TestConstants.TWEET_ID));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getUserTweets() {
        List<TweetUserProjection> tweetUserProjections = TweetServiceTestHelper.createMockTweetUserProjectionList();
        List<RetweetProjection> retweetProjections = TweetServiceTestHelper.createMockRetweetProjectionList();
        Page<TweetUserProjection> pageableTweetUserProjections = new PageImpl<>(tweetUserProjections, pageable, 20);
        int totalPages = tweetUserProjections.size() + retweetProjections.size();
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        when(tweetRepository.getTweetsByUserId(TestConstants.USER_ID)).thenReturn(tweetUserProjections);
        when(retweetRepository.getRetweetsByUserId(TestConstants.USER_ID)).thenReturn(retweetProjections);
        when(tweetServiceHelper.combineTweetsArrays(tweetUserProjections, retweetProjections))
                .thenReturn(tweetUserProjections);
        when(userClient.getUserPinnedTweetId(TestConstants.USER_ID)).thenReturn(null);
        when(tweetServiceHelper.getPageableTweetProjectionList(pageable, tweetUserProjections, totalPages))
                .thenReturn(pageableTweetUserProjections);
        assertEquals(pageableTweetUserProjections, tweetService.getUserTweets(TestConstants.USER_ID, pageable));
        verify(userClient, times(1)).isUserExists(TestConstants.USER_ID);
        verify(tweetRepository, times(1)).getTweetsByUserId(TestConstants.USER_ID);
        verify(retweetRepository, times(1)).getRetweetsByUserId(TestConstants.USER_ID);
        verify(tweetServiceHelper, times(1)).combineTweetsArrays(tweetUserProjections, retweetProjections);
        verify(userClient, times(1)).getUserPinnedTweetId(TestConstants.USER_ID);
        verify(tweetServiceHelper, times(1)).getPageableTweetProjectionList(pageable, tweetUserProjections, totalPages);
    }

    @Test
    void getUserTweets_ShouldRemovePinnedTweet() {
        TweetUserProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetUserProjection.class);
        List<TweetUserProjection> tweetUserProjections = new ArrayList<>(TweetServiceTestHelper.createMockTweetUserProjectionList());
        List<RetweetProjection> retweetProjections = TweetServiceTestHelper.createMockRetweetProjectionList();
        Page<TweetUserProjection> pageableTweetUserProjections = new PageImpl<>(tweetUserProjections, pageable, 20);
        int totalPages = tweetUserProjections.size() + retweetProjections.size();
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        when(tweetRepository.getTweetsByUserId(TestConstants.USER_ID)).thenReturn(tweetUserProjections);
        when(retweetRepository.getRetweetsByUserId(TestConstants.USER_ID)).thenReturn(retweetProjections);
        when(tweetServiceHelper.combineTweetsArrays(tweetUserProjections, retweetProjections))
                .thenReturn(tweetUserProjections);
        when(userClient.getUserPinnedTweetId(TestConstants.USER_ID)).thenReturn(TestConstants.TWEET_ID);
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetUserProjection.class)).thenReturn(Optional.of(tweetProjection));
        when(tweetServiceHelper.getPageableTweetProjectionList(pageable, tweetUserProjections, totalPages))
                .thenReturn(pageableTweetUserProjections);
        assertEquals(pageableTweetUserProjections, tweetService.getUserTweets(TestConstants.USER_ID, pageable));
        verify(userClient, times(1)).isUserExists(TestConstants.USER_ID);
        verify(tweetRepository, times(1)).getTweetsByUserId(TestConstants.USER_ID);
        verify(retweetRepository, times(1)).getRetweetsByUserId(TestConstants.USER_ID);
        verify(tweetServiceHelper, times(1)).combineTweetsArrays(tweetUserProjections, retweetProjections);
        verify(userClient, times(1)).getUserPinnedTweetId(TestConstants.USER_ID);
        verify(tweetRepository, times(1)).getTweetById(TestConstants.TWEET_ID, TweetUserProjection.class);
        verify(tweetServiceHelper, times(1)).getPageableTweetProjectionList(pageable, tweetUserProjections, totalPages);
    }

    @Test
    void getUserTweets_ShouldUserIdNotFound() {
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getUserTweets(TestConstants.USER_ID, pageable));
        assertEquals(String.format(USER_ID_NOT_FOUND, TestConstants.USER_ID), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getUserTweets_ShouldUserNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getUserTweets(TestConstants.USER_ID, pageable));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getUserTweets_ShouldUserProfileBlocked() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getUserTweets(TestConstants.USER_ID, pageable));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getUserMediaTweets() {
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        when(tweetRepository.getUserMediaTweets(TestConstants.USER_ID, pageable)).thenReturn(pageableTweetProjections);
        assertEquals(pageableTweetProjections, tweetService.getUserMediaTweets(TestConstants.USER_ID, pageable));
        verify(userClient, times(1)).isUserExists(TestConstants.USER_ID);
        verify(tweetRepository, times(1)).getUserMediaTweets(TestConstants.USER_ID, pageable);
    }

    @Test
    void getUserMediaTweets_ShouldUserIdNotFound() {
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getUserMediaTweets(TestConstants.USER_ID, pageable));
        assertEquals(String.format(USER_ID_NOT_FOUND, TestConstants.USER_ID), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getUserMediaTweets_ShouldUserNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getUserMediaTweets(TestConstants.USER_ID, pageable));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getUserMediaTweets_ShouldUserProfileBlocked() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getUserMediaTweets(TestConstants.USER_ID, pageable));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getUserTweetImages() {
        List<ProfileTweetImageProjection> mockProfileTweetImageProjections = TweetServiceTestHelper.createMockProfileTweetImageProjections();
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        when(tweetRepository.getUserTweetImages(TestConstants.USER_ID, PageRequest.of(0, 6)))
                .thenReturn(mockProfileTweetImageProjections);
        assertEquals(mockProfileTweetImageProjections, tweetService.getUserTweetImages(TestConstants.USER_ID));
        verify(userClient, times(1)).isUserExists(TestConstants.USER_ID);
        verify(tweetRepository, times(1)).getUserTweetImages(TestConstants.USER_ID, PageRequest.of(0, 6));
    }

    @Test
    void getUserTweetImages_ShouldUserIdNotFound() {
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getUserTweetImages(TestConstants.USER_ID));
        assertEquals(String.format(USER_ID_NOT_FOUND, TestConstants.USER_ID), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getUserTweetImages_ShouldUserNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getUserTweetImages(TestConstants.USER_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getUserTweetImages_ShouldUserProfileBlocked() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        when(userClient.isUserExists(TestConstants.USER_ID)).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getUserTweetImages(TestConstants.USER_ID));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getTweetAdditionalInfoById() {
        TweetAdditionalInfoProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetAdditionalInfoProjection.class);
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetAdditionalInfoProjection.class)).thenReturn(Optional.of(tweetProjection));
        assertEquals(tweetProjection, tweetService.getTweetAdditionalInfoById(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).getTweetById(TestConstants.TWEET_ID, TweetAdditionalInfoProjection.class);
    }

    @Test
    void getTweetAdditionalInfoById_ShouldTweetNotFound() {
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetAdditionalInfoProjection.class)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTweetAdditionalInfoById(TestConstants.TWEET_ID));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getTweetAdditionalInfoById_ShouldTweetDeleted() {
        TweetAdditionalInfoProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(true, TweetAdditionalInfoProjection.class);
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetAdditionalInfoProjection.class)).thenReturn(Optional.of(tweetProjection));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTweetAdditionalInfoById(TestConstants.TWEET_ID));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getTweetAdditionalInfoById_ShouldUserNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        TweetAdditionalInfoProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetAdditionalInfoProjection.class);
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetAdditionalInfoProjection.class)).thenReturn(Optional.of(tweetProjection));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTweetAdditionalInfoById(TestConstants.TWEET_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getTweetAdditionalInfoById_ShouldUserProfileBlocked() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        TweetAdditionalInfoProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetAdditionalInfoProjection.class);
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetAdditionalInfoProjection.class)).thenReturn(Optional.of(tweetProjection));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTweetAdditionalInfoById(TestConstants.TWEET_ID));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getRepliesByTweetId() {
        Tweet tweet = new Tweet();
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(tweetRepository.getRepliesByTweetId(TestConstants.TWEET_ID)).thenReturn(tweetProjections);
        assertEquals(tweetProjections, tweetService.getRepliesByTweetId(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(tweetRepository, times(1)).getRepliesByTweetId(TestConstants.TWEET_ID);
    }

    @Test
    void getRepliesByTweetId_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getRepliesByTweetId(TestConstants.TWEET_ID));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getRepliesByTweetId_ShouldTweetDeleted() {
        Tweet tweet = new Tweet();
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getRepliesByTweetId(TestConstants.TWEET_ID));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getRepliesByTweetId_ShouldUserNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getRepliesByTweetId(TestConstants.TWEET_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getRepliesByTweetId_ShouldUserProfileBlocked() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getRepliesByTweetId(TestConstants.TWEET_ID));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getQuotesByTweetId() {
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(tweetRepository.getTweetAuthorIds()).thenReturn(ids);
        when(userClient.getValidUserIds(new IdsRequest(ids))).thenReturn(ids);
        when(tweetRepository.getQuotesByTweetId(ids, TestConstants.TWEET_ID, pageable)).thenReturn(pageableTweetProjections);
        assertEquals(pageableTweetProjections, tweetService.getQuotesByTweetId(pageable, TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(tweetRepository, times(1)).getTweetAuthorIds();
        verify(userClient, times(1)).getValidUserIds(new IdsRequest(ids));
    }

    @Test
    void getQuotesByTweetId_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getQuotesByTweetId(pageable, TestConstants.TWEET_ID));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getQuotesByTweetId_ShouldTweetDeleted() {
        Tweet tweet = new Tweet();
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getQuotesByTweetId(pageable, TestConstants.TWEET_ID));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getQuotesByTweetId_ShouldUserNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getQuotesByTweetId(pageable, TestConstants.TWEET_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getQuotesByTweetId_ShouldUserProfileBlocked() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getQuotesByTweetId(pageable, TestConstants.TWEET_ID));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getMediaTweets() {
        when(tweetRepository.getTweetAuthorIds()).thenReturn(ids);
        when(userClient.getValidUserIds(new IdsRequest(ids))).thenReturn(ids);
        when(tweetRepository.getMediaTweets(ids, pageable)).thenReturn(pageableTweetProjections);
        assertEquals(pageableTweetProjections, tweetService.getMediaTweets(pageable));
        verify(tweetRepository, times(1)).getTweetAuthorIds();
        verify(userClient, times(1)).getValidUserIds(new IdsRequest(ids));
        verify(tweetRepository, times(1)).getMediaTweets(ids, pageable);
    }

    @Test
    void getTweetsWithVideo() {
        when(tweetRepository.getTweetAuthorIds()).thenReturn(ids);
        when(userClient.getValidUserIds(new IdsRequest(ids))).thenReturn(ids);
        when(tweetRepository.getTweetsWithVideo(ids, pageable)).thenReturn(pageableTweetProjections);
        assertEquals(pageableTweetProjections, tweetService.getTweetsWithVideo(pageable));
        verify(tweetRepository, times(1)).getTweetAuthorIds();
        verify(userClient, times(1)).getValidUserIds(new IdsRequest(ids));
        verify(tweetRepository, times(1)).getTweetsWithVideo(ids, pageable);
    }

    @Test
    void getFollowersTweets() {
        when(userClient.getUserFollowersIds()).thenReturn(ids);
        when(tweetRepository.getFollowersTweets(ids, pageable)).thenReturn(pageableTweetProjections);
        assertEquals(pageableTweetProjections, tweetService.getFollowersTweets(pageable));
        verify(userClient, times(1)).getUserFollowersIds();
        verify(tweetRepository, times(1)).getFollowersTweets(ids, pageable);
    }

    @Test
    void getTaggedImageUsers() {
        Tweet tweet = new Tweet();
        tweet.setAuthorId(TestConstants.USER_ID);
        HeaderResponse<UserResponse> headerResponse = new HeaderResponse<>(
                List.of(new UserResponse(), new UserResponse()), new HttpHeaders());
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(tweetRepository.getTaggedImageUserIds(TestConstants.TWEET_ID)).thenReturn(ids);
        when(userClient.getUsersByIds(new IdsRequest(ids), pageable)).thenReturn(headerResponse);
        assertEquals(headerResponse, tweetService.getTaggedImageUsers(TestConstants.TWEET_ID, pageable));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(tweetRepository, times(1)).getTaggedImageUserIds(TestConstants.TWEET_ID);
        verify(userClient, times(1)).getUsersByIds(new IdsRequest(ids), pageable);
    }

    @Test
    void getTaggedImageUsers_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTaggedImageUsers(TestConstants.TWEET_ID, pageable));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getTaggedImageUsers_ShouldTweetDeleted() {
        Tweet tweet = new Tweet();
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTaggedImageUsers(TestConstants.TWEET_ID, pageable));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getTaggedImageUsers_ShouldUserNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTaggedImageUsers(TestConstants.TWEET_ID, pageable));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void getTaggedImageUsers_ShouldUserProfileBlocked() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.getTaggedImageUsers(TestConstants.TWEET_ID, pageable));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void createNewTweet() {
        Tweet tweet = new Tweet();
        tweet.setText("test tweet");
        TweetResponse tweetResponse = new TweetResponse();
        tweetResponse.setText("test tweet");
        when(tweetServiceHelper.createTweet(tweet)).thenReturn(tweetResponse);
        assertEquals(tweetResponse, tweetService.createNewTweet(tweet));
        verify(tweetServiceHelper, times(1)).createTweet(tweet);
    }

    @Test
    void deleteTweet() {
        when(tweetRepository.getTweetByUserId(TestConstants.USER_ID, TestConstants.TWEET_ID)).thenReturn(Optional.of(new Tweet()));
        assertEquals("Your Tweet was deleted", tweetService.deleteTweet(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).getTweetByUserId(TestConstants.USER_ID, TestConstants.TWEET_ID);
        verify(userClient, times(1)).updatePinnedTweetId(TestConstants.TWEET_ID);
        verify(tagClient, times(1)).deleteTagsByTweetId(TestConstants.TWEET_ID);
    }

    @Test
    void deleteTweet_ShouldTweetNotFound() {
        when(tweetRepository.getTweetByUserId(TestConstants.USER_ID, TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.deleteTweet(TestConstants.TWEET_ID));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void searchTweets() {
        String testText = "test text";
        when(tweetRepository.getUserIdsByTweetText(testText)).thenReturn(ids);
        when(userClient.getValidTweetUserIds(new IdsRequest(ids), testText)).thenReturn(ids);
        when(tweetRepository.searchTweets(testText, ids, pageable)).thenReturn(pageableTweetProjections);
        assertEquals(pageableTweetProjections, tweetService.searchTweets(testText, pageable));
        verify(tweetRepository, times(1)).getUserIdsByTweetText(testText);
        verify(userClient, times(1)).getValidTweetUserIds(new IdsRequest(ids), testText);
        verify(tweetRepository, times(1)).searchTweets(testText, ids, pageable);
    }

    @Test
    void replyTweet() {
        Tweet tweet = new Tweet();
        tweet.setAuthorId(TestConstants.USER_ID);
        TweetResponse tweetResponse = new TweetResponse();
        tweetResponse.setId(TestConstants.TWEET_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(tweetServiceHelper.createTweet(tweet)).thenReturn(tweetResponse);
        assertEquals(tweetResponse, tweetService.replyTweet(TestConstants.TWEET_ID, tweet));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(tweetServiceHelper, times(1)).createTweet(tweet);
        verify(tweetRepository, times(1)).addReply(TestConstants.TWEET_ID, TestConstants.TWEET_ID);
    }

    @Test
    void replyTweet_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.replyTweet(TestConstants.TWEET_ID, new Tweet()));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void replyTweet_ShouldTweetDeleted() {
        Tweet tweet = new Tweet();
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.replyTweet(TestConstants.TWEET_ID, tweet));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void replyTweet_ShouldUserNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.replyTweet(TestConstants.TWEET_ID, tweet));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void replyTweet_ShouldUserProfileBlocked() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.replyTweet(TestConstants.TWEET_ID, tweet));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void quoteTweet() {
        Tweet tweet = new Tweet();
        tweet.setAuthorId(TestConstants.USER_ID);
        TweetResponse tweetResponse = new TweetResponse();
        tweetResponse.setId(TestConstants.TWEET_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(tweetServiceHelper.createTweet(tweet)).thenReturn(tweetResponse);
        assertEquals(tweetResponse, tweetService.quoteTweet(TestConstants.TWEET_ID, tweet));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(tweetServiceHelper, times(1)).createTweet(tweet);
        verify(tweetRepository, times(1)).addQuote(TestConstants.TWEET_ID, TestConstants.TWEET_ID);
    }

    @Test
    void quoteTweet_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.quoteTweet(TestConstants.TWEET_ID, new Tweet()));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void quoteTweet_ShouldTweetDeleted() {
        Tweet tweet = new Tweet();
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.quoteTweet(TestConstants.TWEET_ID, new Tweet()));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void quoteTweet_ShouldUserNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.quoteTweet(TestConstants.TWEET_ID, new Tweet()));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void quoteTweet_ShouldUserProfileBlocked() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setDeleted(false);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(TestConstants.USER_ID)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(TestConstants.USER_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.quoteTweet(TestConstants.TWEET_ID, new Tweet()));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void changeTweetReplyType() {
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        Tweet tweet = new Tweet();
        tweet.setId(TestConstants.TWEET_ID);
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.getTweetByAuthorIdAndTweetId(TestConstants.TWEET_ID, TestConstants.USER_ID))
                .thenReturn(Optional.of(tweet));
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetProjection.class))
                .thenReturn(Optional.of(tweetProjection));
        assertEquals(tweetProjection, tweetService.changeTweetReplyType(TestConstants.TWEET_ID, ReplyType.MENTION));
        verify(tweetRepository, times(1)).getTweetByAuthorIdAndTweetId(TestConstants.TWEET_ID, TestConstants.USER_ID);
        verify(tweetRepository, times(1)).getTweetById(TestConstants.TWEET_ID, TweetProjection.class);
    }

    @Test
    void changeTweetReplyType_ShouldTweetNotFound() {
        when(tweetRepository.getTweetByAuthorIdAndTweetId(TestConstants.TWEET_ID, TestConstants.USER_ID))
                .thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.changeTweetReplyType(TestConstants.TWEET_ID, ReplyType.MENTION));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void changeTweetReplyType_ShouldAuthorTweetNotFound() {
        TweetServiceTestHelper.mockAuthenticatedUserId();
        Tweet tweet = new Tweet();
        tweet.setAuthorId(TestConstants.USER_ID);
        when(tweetRepository.getTweetByAuthorIdAndTweetId(TestConstants.TWEET_ID, TestConstants.USER_ID))
                .thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> tweetService.changeTweetReplyType(TestConstants.TWEET_ID, ReplyType.MENTION));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
