package com.gmail.javacoded78.integration.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.TweetUserResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.integration.service.TweetServiceTestHelper;
import com.gmail.javacoded78.mapper.BasicMapper;
import com.gmail.javacoded78.mapper.RetweetMapper;
import com.gmail.javacoded78.repository.projection.TweetUserProjection;
import com.gmail.javacoded78.service.RetweetService;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
class RetweetMapperTest extends AbstractAuthTest {

    private final RetweetMapper retweetMapper;

    @MockBean
    private final BasicMapper basicMapper;

    @MockBean
    private final RetweetService retweetService;

    @Test
    void getUserRetweetsAndReplies() {
        List<TweetUserProjection> tweetUserProjections = TweetServiceTestHelper.createMockTweetUserProjectionList();
        Page<TweetUserProjection> pageableTweetUserProjections = new PageImpl<>(tweetUserProjections, pageable, 20);
        HeaderResponse<TweetUserResponse> headerResponse = new HeaderResponse<>(
                List.of(new TweetUserResponse(), new TweetUserResponse()), new HttpHeaders());
        when(retweetService.getUserRetweetsAndReplies(TestConstants.USER_ID, pageable)).thenReturn(pageableTweetUserProjections);
        when(basicMapper.getHeaderResponse(pageableTweetUserProjections, TweetUserResponse.class)).thenReturn(headerResponse);
        assertEquals(headerResponse, retweetMapper.getUserRetweetsAndReplies(TestConstants.USER_ID, pageable));
        verify(retweetService, times(1)).getUserRetweetsAndReplies(TestConstants.USER_ID, pageable);
        verify(basicMapper, times(1)).getHeaderResponse(pageableTweetUserProjections, TweetUserResponse.class);
    }

    @Test
    void getRetweetedUsersByTweetId() {
        HeaderResponse<UserResponse> headerResponse = new HeaderResponse<>(
                List.of(new UserResponse(), new UserResponse()), new HttpHeaders());
        when(retweetService.getRetweetedUsersByTweetId(TestConstants.TWEET_ID, pageable)).thenReturn(headerResponse);
        assertEquals(headerResponse, retweetMapper.getRetweetedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        verify(retweetService, times(1)).getRetweetedUsersByTweetId(TestConstants.TWEET_ID, pageable);
    }

    @Test
    void retweet() {
        NotificationResponse notificationResponse = new NotificationResponse();
        when(retweetService.retweet(TestConstants.TWEET_ID)).thenReturn(notificationResponse);
        assertEquals(notificationResponse, retweetMapper.retweet(TestConstants.TWEET_ID));
        verify(retweetService, times(1)).retweet(TestConstants.TWEET_ID);
    }
}
