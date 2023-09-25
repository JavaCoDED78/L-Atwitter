package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.ProfileTweetImageResponse;
import com.gmail.javacoded78.dto.response.TweetAdditionalInfoResponse;
import com.gmail.javacoded78.dto.response.TweetUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.integration.service.TweetServiceTestHelper;
import com.gmail.javacoded78.repository.projection.ProfileTweetImageProjection;
import com.gmail.javacoded78.repository.projection.TweetAdditionalInfoProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.repository.projection.TweetUserProjection;
import com.gmail.javacoded78.service.TweetService;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class TweetMapperTest {

    private final TweetMapper tweetMapper;

    @MockBean
    private final BasicMapper basicMapper;

    @MockBean
    private final TweetService tweetService;

    private static final PageRequest pageable = PageRequest.of(0, 20);
    private static final List<TweetProjection> tweetProjections = Arrays.asList(
            TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class),
            TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class));
    private static final Page<TweetProjection> pageableTweetProjections = new PageImpl<>(tweetProjections, pageable, 20);
    private static final HeaderResponse<TweetResponse> headerResponse = new HeaderResponse<>(
            List.of(new TweetResponse(), new TweetResponse()), new HttpHeaders());

    @Test
    void getTweets() {
        when(tweetService.getTweets(pageable)).thenReturn(pageableTweetProjections);
        when(basicMapper.getHeaderResponse(pageableTweetProjections, TweetResponse.class)).thenReturn(headerResponse);
        assertEquals(headerResponse, tweetMapper.getTweets(pageable));
        verify(tweetService, times(1)).getTweets(pageable);
        verify(basicMapper, times(1)).getHeaderResponse(pageableTweetProjections, TweetResponse.class);
    }

    @Test
    void getTweetById() {
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        when(tweetService.getTweetById(TestConstants.TWEET_ID)).thenReturn(tweetProjection);
        when(basicMapper.convertToResponse(tweetProjection, TweetResponse.class)).thenReturn(new TweetResponse());
        assertEquals(new TweetResponse(), tweetMapper.getTweetById(TestConstants.TWEET_ID));
        verify(tweetService, times(1)).getTweetById(TestConstants.TWEET_ID);
        verify(basicMapper, times(1)).convertToResponse(tweetProjection, TweetResponse.class);
    }

    @Test
    void getUserTweets() {
        List<TweetUserProjection> tweetUserProjections = TweetServiceTestHelper.createMockTweetUserProjectionList();
        Page<TweetUserProjection> pageableTweetUserProjections = new PageImpl<>(tweetUserProjections, pageable, 20);
        HeaderResponse<TweetUserResponse> headerResponse = new HeaderResponse<>(
                List.of(new TweetUserResponse(), new TweetUserResponse()), new HttpHeaders());
        when(tweetService.getUserTweets(TestConstants.USER_ID, pageable)).thenReturn(pageableTweetUserProjections);
        when(basicMapper.getHeaderResponse(pageableTweetUserProjections, TweetUserResponse.class)).thenReturn(headerResponse);
        assertEquals(headerResponse, tweetMapper.getUserTweets(TestConstants.USER_ID, pageable));
        verify(tweetService, times(1)).getUserTweets(TestConstants.USER_ID, pageable);
        verify(basicMapper, times(1)).getHeaderResponse(pageableTweetUserProjections, TweetUserResponse.class);
    }

    @Test
    void getUserMediaTweets() {
        when(tweetService.getUserMediaTweets(TestConstants.USER_ID, pageable)).thenReturn(pageableTweetProjections);
        when(basicMapper.getHeaderResponse(pageableTweetProjections, TweetResponse.class)).thenReturn(headerResponse);
        assertEquals(headerResponse, tweetMapper.getUserMediaTweets(TestConstants.USER_ID, pageable));
        verify(tweetService, times(1)).getUserMediaTweets(TestConstants.USER_ID, pageable);
        verify(basicMapper, times(1)).getHeaderResponse(pageableTweetProjections, TweetResponse.class);
    }

    @Test
    void getUserTweetImages() {
        List<ProfileTweetImageResponse> responses = List.of(new ProfileTweetImageResponse(), new ProfileTweetImageResponse());
        List<ProfileTweetImageProjection> mockProfileTweetImage = TweetServiceTestHelper.createMockProfileTweetImageProjections();
        when(tweetService.getUserTweetImages(TestConstants.USER_ID)).thenReturn(mockProfileTweetImage);
        when(basicMapper.convertToResponseList(mockProfileTweetImage, ProfileTweetImageResponse.class)).thenReturn(responses);
        assertEquals(responses, tweetMapper.getUserTweetImages(TestConstants.USER_ID));
        verify(tweetService, times(1)).getUserTweetImages(TestConstants.USER_ID);
        verify(basicMapper, times(1)).convertToResponseList(mockProfileTweetImage, ProfileTweetImageResponse.class);
    }

    @Test
    void getTweetAdditionalInfoById() {
        TweetAdditionalInfoProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetAdditionalInfoProjection.class);
        when(tweetService.getTweetAdditionalInfoById(TestConstants.TWEET_ID)).thenReturn(tweetProjection);
        when(basicMapper.convertToResponse(tweetProjection, TweetAdditionalInfoResponse.class)).thenReturn(new TweetAdditionalInfoResponse());
        assertEquals(new TweetAdditionalInfoResponse(), tweetMapper.getTweetAdditionalInfoById(TestConstants.TWEET_ID));
        verify(tweetService, times(1)).getTweetAdditionalInfoById(TestConstants.TWEET_ID);
        verify(basicMapper, times(1)).convertToResponse(tweetProjection, TweetAdditionalInfoResponse.class);
    }

    @Test
    void getRepliesByTweetId() {
        List<TweetResponse> tweetResponses = List.of(new TweetResponse(), new TweetResponse());
        when(tweetService.getRepliesByTweetId(TestConstants.TWEET_ID)).thenReturn(tweetProjections);
        when(basicMapper.convertToResponseList(tweetProjections, TweetResponse.class)).thenReturn(tweetResponses);
        assertEquals(tweetResponses, tweetMapper.getRepliesByTweetId(TestConstants.TWEET_ID));
        verify(tweetService, times(1)).getRepliesByTweetId(TestConstants.TWEET_ID);
        verify(basicMapper, times(1)).convertToResponseList(tweetProjections, TweetResponse.class);
    }
}
