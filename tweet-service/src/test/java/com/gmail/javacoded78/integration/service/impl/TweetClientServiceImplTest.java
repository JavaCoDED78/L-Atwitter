package com.gmail.javacoded78.integration.service.impl;

import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.integration.service.TweetServiceTestHelper;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.ChatTweetProjection;
import com.gmail.javacoded78.repository.projection.NotificationTweetProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.service.impl.TweetClientServiceImpl;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
class TweetClientServiceImplTest extends AbstractAuthTest {

    private final TweetClientServiceImpl tweetClientService;

    @MockBean
    private final TweetRepository tweetRepository;

    private static final IdsRequest idsRequest = new IdsRequest(ids);
    private static final List<TweetProjection> tweetProjections = Arrays.asList(
            TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class),
            TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class));
    private static final Page<TweetProjection> pageableTweetProjections = new PageImpl<>(tweetProjections, pageable, 20);

    @BeforeEach
    public void setUp() {
        super.setUp();
    }
    @Test
    void getTweetsByIds() {
        when(tweetRepository.getTweetListsByIds(ids)).thenReturn(tweetProjections);
        assertEquals(tweetProjections, tweetClientService.getTweetsByIds(idsRequest));
        verify(tweetRepository, times(1)).getTweetListsByIds(ids);
    }

    @Test
    void getTweetsByUserIds() {
        when(tweetRepository.getTweetsByAuthorIds(ids, pageable)).thenReturn(pageableTweetProjections);
        assertEquals(pageableTweetProjections, tweetClientService.getTweetsByUserIds(idsRequest, pageable));
        verify(tweetRepository, times(1)).getTweetsByAuthorIds(ids, pageable);
    }

    @Test
    void getTweetById() {
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, TweetProjection.class)).thenReturn(Optional.of(tweetProjection));
        assertEquals(tweetProjection, tweetClientService.getTweetById(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).getTweetById(TestConstants.TWEET_ID, TweetProjection.class);
    }

    @Test
    void getTweetsByIds_Pageable() {
        when(tweetRepository.getTweetsByIds(ids, pageable)).thenReturn(pageableTweetProjections);
        assertEquals(pageableTweetProjections, tweetClientService.getTweetsByIds(idsRequest, pageable));
        verify(tweetRepository, times(1)).getTweetsByIds(ids, pageable);
    }

    @Test
    void getNotificationTweet() {
        NotificationTweetProjection notificationTweetProjection = TweetServiceTestHelper.createNotificationTweetProjection();

        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, NotificationTweetProjection.class))
                .thenReturn(Optional.of(notificationTweetProjection));
        assertEquals(notificationTweetProjection, tweetClientService.getNotificationTweet(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).getTweetById(TestConstants.TWEET_ID, NotificationTweetProjection.class);
    }

    @Test
    void isTweetExists() {
        when(tweetRepository.isTweetExists(TestConstants.TWEET_ID)).thenReturn(true);
        assertTrue(tweetClientService.isTweetExists(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).isTweetExists(TestConstants.TWEET_ID);
    }

    @Test
    void getTweetCountByText() {
        when(tweetRepository.getTweetCountByText("test text")).thenReturn(123L);
        assertEquals(123L, tweetClientService.getTweetCountByText("test text"));
        verify(tweetRepository, times(1)).getTweetCountByText("test text");
    }

    @Test
    void getChatTweet() {
        ChatTweetProjection chatTweetProjection = TweetServiceTestHelper.createChatTweetProjection();
        when(tweetRepository.getTweetById(TestConstants.TWEET_ID, ChatTweetProjection.class))
                .thenReturn(Optional.of(chatTweetProjection));
        assertEquals(chatTweetProjection, tweetClientService.getChatTweet(TestConstants.TWEET_ID));
        verify(tweetRepository, times(1)).getTweetById(TestConstants.TWEET_ID, ChatTweetProjection.class);
    }
}
