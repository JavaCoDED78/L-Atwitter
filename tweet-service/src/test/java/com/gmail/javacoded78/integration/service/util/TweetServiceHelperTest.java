package com.gmail.javacoded78.integration.service.util;

import com.gmail.javacoded78.dto.request.TweetTextRequest;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.feign.NotificationClient;
import com.gmail.javacoded78.feign.TagClient;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.mapper.BasicMapper;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.model.TweetImage;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.integration.service.TweetServiceTestHelper;
import com.gmail.javacoded78.service.util.TweetServiceHelper;
import com.gmail.javacoded78.service.util.TweetValidationHelper;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class TweetServiceHelperTest {

    private final TweetServiceHelper tweetServiceHelper;

    @MockBean
    private final TweetRepository tweetRepository;

    @MockBean
    private final TweetValidationHelper tweetValidationHelper;

    @MockBean
    private final NotificationClient notificationClient;

    @MockBean
    private final UserClient userClient;

    @MockBean
    private final TagClient tagClient;

    @MockBean
    private final BasicMapper basicMapper;

    @BeforeEach
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    void createTweet() {
        Tweet tweet = new Tweet();
        tweet.setId(TestConstants.TWEET_ID);
        tweet.setText("test text");
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        TweetResponse tweetResponse = new TweetResponse();
        tweetResponse.setText("test text");
        when(tweetRepository.getTweetById(tweet.getId(), TweetProjection.class)).thenReturn(Optional.of(tweetProjection));
        when(basicMapper.convertToResponse(tweetProjection, TweetResponse.class)).thenReturn(tweetResponse);
        assertEquals(tweetResponse, tweetServiceHelper.createTweet(tweet));
        verify(tweetValidationHelper, times(1)).checkTweetTextLength(tweet.getText());
        verify(tweetRepository, times(1)).save(tweet);
        verify(userClient, times(1)).updateTweetCount(true);
        verify(tweetRepository, times(1)).getTweetById(tweet.getId(), TweetProjection.class);
        verify(basicMapper, times(1)).convertToResponse(tweetProjection, TweetResponse.class);
        verify(tagClient, times(1)).parseHashtagsFromText(tweet.getId(), new TweetTextRequest(tweet.getText()));
        verify(notificationClient, times(1)).sendTweetNotificationToSubscribers(tweet.getId());
    }

    @Test
    void createTweetWithImage() {
        Tweet tweet = new Tweet();
        tweet.setId(TestConstants.TWEET_ID);
        tweet.setText("test text");
        tweet.setImages(List.of(new TweetImage()));
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        TweetResponse tweetResponse = new TweetResponse();
        tweetResponse.setText("test text");
        when(tweetRepository.getTweetById(tweet.getId(), TweetProjection.class)).thenReturn(Optional.of(tweetProjection));
        when(basicMapper.convertToResponse(tweetProjection, TweetResponse.class)).thenReturn(tweetResponse);
        assertEquals(tweetResponse, tweetServiceHelper.createTweet(tweet));
        verify(tweetValidationHelper, times(1)).checkTweetTextLength(tweet.getText());
        verify(tweetRepository, times(1)).save(tweet);
        verify(userClient, times(1)).updateMediaTweetCount(true);
        verify(tweetRepository, times(1)).getTweetById(tweet.getId(), TweetProjection.class);
        verify(basicMapper, times(1)).convertToResponse(tweetProjection, TweetResponse.class);
        verify(tagClient, times(1)).parseHashtagsFromText(tweet.getId(), new TweetTextRequest(tweet.getText()));
        verify(notificationClient, times(1)).sendTweetNotificationToSubscribers(tweet.getId());
    }

    @Test
    void createTweetAndParseMetadataFromUrlLink() {
        Tweet tweet = new Tweet();
        tweet.setId(TestConstants.TWEET_ID);
        tweet.setText(TestConstants.TWEET_TEXT);
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        TweetResponse tweetResponse = new TweetResponse();
        tweetResponse.setText(TestConstants.TWEET_TEXT);
        tweetResponse.setLink(TestConstants.LINK);
        when(tweetRepository.getTweetById(tweet.getId(), TweetProjection.class)).thenReturn(Optional.of(tweetProjection));
        when(basicMapper.convertToResponse(tweetProjection, TweetResponse.class)).thenReturn(tweetResponse);
        TweetResponse response = tweetServiceHelper.createTweet(tweet);
        assertEquals(tweetResponse, response);
        assertEquals(TestConstants.LINK, response.getLink());
        verify(tweetValidationHelper, times(1)).checkTweetTextLength(tweet.getText());
        verify(tweetRepository, times(1)).save(tweet);
        verify(userClient, times(1)).updateTweetCount(true);
        verify(tweetRepository, times(1)).getTweetById(tweet.getId(), TweetProjection.class);
        verify(basicMapper, times(1)).convertToResponse(tweetProjection, TweetResponse.class);
        verify(tagClient, times(1)).parseHashtagsFromText(tweet.getId(), new TweetTextRequest(tweet.getText()));
        verify(notificationClient, times(1)).sendTweetNotificationToSubscribers(tweet.getId());
    }
}