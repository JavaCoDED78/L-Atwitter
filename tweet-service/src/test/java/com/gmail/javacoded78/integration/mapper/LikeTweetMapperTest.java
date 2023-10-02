package com.gmail.javacoded78.integration.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.mapper.LikeTweetMapper;
import com.gmail.javacoded78.service.LikeTweetService;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
class LikeTweetMapperTest extends AbstractAuthTest {

    private final LikeTweetMapper likeTweetMapper;

    @MockBean
    private final LikeTweetService likeTweetService;

    @Test
    void getLikedUsersByTweetId() {
        HeaderResponse<UserResponse> headerResponse = new HeaderResponse<>(
                List.of(new UserResponse(), new UserResponse()), new HttpHeaders());
        when(likeTweetService.getLikedUsersByTweetId(TestConstants.TWEET_ID, pageable)).thenReturn(headerResponse);
        Assertions.assertEquals(headerResponse, likeTweetMapper.getLikedUsersByTweetId(TestConstants.TWEET_ID, pageable));
        verify(likeTweetService, times(1)).getLikedUsersByTweetId(TestConstants.TWEET_ID, pageable);
    }

    @Test
    void likeTweet() {
        NotificationResponse notificationResponse = new NotificationResponse();
        when(likeTweetService.likeTweet(TestConstants.TWEET_ID)).thenReturn(notificationResponse);
        Assertions.assertEquals(notificationResponse, likeTweetMapper.likeTweet(TestConstants.TWEET_ID));
        verify(likeTweetService, times(1)).likeTweet(TestConstants.TWEET_ID);
    }
}
