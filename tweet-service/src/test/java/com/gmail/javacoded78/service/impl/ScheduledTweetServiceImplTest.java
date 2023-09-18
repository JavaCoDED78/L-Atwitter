package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.TweetProjection;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_TWEET_TEXT_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ScheduledTweetServiceImplTest {

    @Autowired
    private ScheduledTweetServiceImpl scheduledTweetService;

    @MockBean
    private TweetRepository tweetRepository;

    @MockBean
    private TweetServiceImpl tweetService;

    @MockBean
    private TweetServiceHelper tweetServiceHelper;

    private final static TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
    private static Tweet tweet;

    @Before
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
        tweet = new Tweet();
        tweet.setText("test text");
    }

    @Test
    public void getScheduledTweets() {
        PageRequest pageable = PageRequest.of(0, 20);
        List<TweetProjection> tweetProjections = Arrays.asList(tweetProjection, tweetProjection);
        Page<TweetProjection> pageableTweetProjections = new PageImpl<>(tweetProjections, pageable, 20);
        when(tweetRepository.getScheduledTweets(TestConstants.USER_ID, pageable)).thenReturn(pageableTweetProjections);
        assertEquals(pageableTweetProjections, scheduledTweetService.getScheduledTweets(pageable));
        verify(tweetRepository, times(1)).getScheduledTweets(TestConstants.USER_ID, pageable);
    }

    @Test
    public void createScheduledTweet() {
        when(tweetService.getTweetById(any())).thenReturn(tweetProjection);
        assertEquals(tweetProjection, scheduledTweetService.createScheduledTweet(tweet));
        verify(tweetServiceHelper, times(1)).parseMetadataFromURL(tweet);
        verify(tweetRepository, times(1)).save(tweet);
        verify(tweetService, times(1)).getTweetById(any());
    }

    @Test
    public void createScheduledTweet_ShouldIncorrectTweetLength() {
        tweet.setText("");
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> scheduledTweetService.createScheduledTweet(tweet));
        assertEquals(INCORRECT_TWEET_TEXT_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void updateScheduledTweet() {
        tweet.setId(TestConstants.TWEET_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(tweetService.getTweetById(TestConstants.TWEET_ID)).thenReturn(tweetProjection);
        assertEquals(tweetProjection, scheduledTweetService.updateScheduledTweet(tweet));
        verify(tweetRepository, times(1)).findById(TestConstants.TWEET_ID);
        verify(tweetService, times(1)).getTweetById(TestConstants.TWEET_ID);
    }

    @Test
    public void updateScheduledTweet_ShouldTweetNotFound() {
        tweet.setId(TestConstants.TWEET_ID);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> scheduledTweetService.updateScheduledTweet(tweet));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void updateScheduledTweet_ShouldIncorrectTweetLength() {
        tweet.setId(TestConstants.TWEET_ID);
        tweet.setText("");
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> scheduledTweetService.updateScheduledTweet(tweet));
        assertEquals(INCORRECT_TWEET_TEXT_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void deleteScheduledTweets() {
        assertEquals("Scheduled tweets deleted.", scheduledTweetService.deleteScheduledTweets(List.of(TestConstants.TWEET_ID)));
        verify(tweetService, times(1)).deleteTweet(TestConstants.TWEET_ID);
    }
}