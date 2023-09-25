package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.chat.ChatTweetResponse;
import com.gmail.javacoded78.dto.response.chat.ChatTweetUserResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationTweetResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.integration.service.TweetServiceTestHelper;
import com.gmail.javacoded78.repository.projection.ChatTweetProjection;
import com.gmail.javacoded78.repository.projection.NotificationTweetProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.service.TweetClientService;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class TweetClientMapperTest {

    private final TweetClientMapper tweetClientMapper;

    @MockBean
    private final BasicMapper basicMapper;

    @MockBean
    private final TweetClientService tweetClientService;

    private static final ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    private static final PageRequest pageable = PageRequest.of(0, 20);
    private static final List<TweetProjection> tweetProjections = Arrays.asList(
            TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class),
            TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class));
    private static final Page<TweetProjection> pageableTweetProjections = new PageImpl<>(tweetProjections, pageable, 20);

    @Test
    void getTweetsByIds() {
        List<TweetResponse> tweetResponses = List.of(new TweetResponse(), new TweetResponse());
        when(tweetClientService.getTweetsByIds(new IdsRequest())).thenReturn(tweetProjections);
        when(basicMapper.convertToResponseList(tweetProjections, TweetResponse.class)).thenReturn(tweetResponses);
        assertEquals(tweetResponses, tweetClientMapper.getTweetsByIds(new IdsRequest()));
        verify(tweetClientService, times(1)).getTweetsByIds(new IdsRequest());
        verify(basicMapper, times(1)).convertToResponseList(tweetProjections, TweetResponse.class);
    }

    @Test
    void getTweetsByUserIds() {
        HeaderResponse<TweetResponse> headerResponse = new HeaderResponse<>(
                List.of(new TweetResponse(), new TweetResponse()), new HttpHeaders());
        when(tweetClientService.getTweetsByUserIds(new IdsRequest(), pageable)).thenReturn(pageableTweetProjections);
        when(basicMapper.getHeaderResponse(pageableTweetProjections, TweetResponse.class)).thenReturn(headerResponse);
        assertEquals(headerResponse, tweetClientMapper.getTweetsByUserIds(new IdsRequest(), pageable));
        verify(tweetClientService, times(1)).getTweetsByUserIds(new IdsRequest(), pageable);
        verify(basicMapper, times(1)).getHeaderResponse(pageableTweetProjections, TweetResponse.class);
    }

    @Test
    void getTweetById() {
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        when(tweetClientService.getTweetById(TestConstants.TWEET_ID)).thenReturn(tweetProjection);
        when(basicMapper.convertToResponse(tweetProjection, TweetResponse.class)).thenReturn(new TweetResponse());
        assertEquals(new TweetResponse(), tweetClientMapper.getTweetById(TestConstants.TWEET_ID));
        verify(tweetClientService, times(1)).getTweetById(TestConstants.TWEET_ID);
        verify(basicMapper, times(1)).convertToResponse(tweetProjection, TweetResponse.class);
    }

    @Test
    void getNotificationTweet() {
        NotificationTweetProjection notificationTweetProjection = factory.createProjection(
                NotificationTweetProjection.class,
                Map.of("id", 1L,
                        "text", "test text",
                        "authorId", TestConstants.USER_ID));
        when(tweetClientService.getNotificationTweet(TestConstants.TWEET_ID)).thenReturn(notificationTweetProjection);
        when(basicMapper.convertToResponse(notificationTweetProjection, NotificationTweetResponse.class))
                .thenReturn(new NotificationTweetResponse());
        assertEquals(new NotificationTweetResponse(), tweetClientMapper.getNotificationTweet(TestConstants.TWEET_ID));
        verify(tweetClientService, times(1)).getNotificationTweet(TestConstants.TWEET_ID);
        verify(basicMapper, times(1)).convertToResponse(notificationTweetProjection, NotificationTweetResponse.class);
    }

    @Test
    void isTweetExists() {
        when(tweetClientService.isTweetExists(TestConstants.TWEET_ID)).thenReturn(true);
        assertTrue(tweetClientMapper.isTweetExists(TestConstants.TWEET_ID));
        verify(tweetClientService, times(1)).isTweetExists(TestConstants.TWEET_ID);
    }

    @Test
    void getTweetCountByText() {
        when(tweetClientService.getTweetCountByText(TestConstants.TWEET_TEXT)).thenReturn(1L);
        assertEquals(1L, tweetClientMapper.getTweetCountByText(TestConstants.TWEET_TEXT));
        verify(tweetClientService, times(1)).getTweetCountByText(TestConstants.TWEET_TEXT);
    }

    @Test
    void getChatTweet() {
        ChatTweetProjection chatTweetProjection = factory.createProjection(
                ChatTweetProjection.class,
                Map.of("id", 1L,
                        "text", "test text",
                        "dateTime", LocalDateTime.now(),
                        "user", new ChatTweetUserResponse(),
                        "authorId", TestConstants.USER_ID,
                        "deleted", false));
        when(tweetClientService.getChatTweet(TestConstants.TWEET_ID)).thenReturn(chatTweetProjection);
        when(basicMapper.convertToResponse(chatTweetProjection, ChatTweetResponse.class)).thenReturn(new ChatTweetResponse());
        assertEquals(new ChatTweetResponse(), tweetClientMapper.getChatTweet(TestConstants.TWEET_ID));
        verify(tweetClientService, times(1)).getChatTweet(TestConstants.TWEET_ID);
        verify(basicMapper, times(1)).convertToResponse(chatTweetProjection, ChatTweetResponse.class);
    }
}
