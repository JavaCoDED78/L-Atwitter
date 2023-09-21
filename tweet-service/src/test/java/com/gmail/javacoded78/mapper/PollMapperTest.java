package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.request.TweetRequest;
import com.gmail.javacoded78.dto.request.VoteRequest;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.integration.service.TweetServiceTestHelper;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.service.PollService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class PollMapperTest {

    private final PollMapper pollMapper;

    @MockBean
    private final BasicMapper basicMapper;

    @MockBean
    private final PollService pollService;

    @Test
    void createPoll() {
        TweetRequest tweetRequest = new TweetRequest();
        Tweet tweet = new Tweet();
        TweetResponse tweetResponse = new TweetResponse();
        when(basicMapper.convertToResponse(tweetRequest, Tweet.class)).thenReturn(tweet);
        when(pollService.createPoll(tweetRequest.getPollDateTime(), tweetRequest.getChoices(), tweet)).thenReturn(tweetResponse);
        assertEquals(tweetResponse, pollMapper.createPoll(tweetRequest));
        verify(basicMapper, times(1)).convertToResponse(tweetRequest, Tweet.class);
        verify(pollService, times(1)).createPoll(tweetRequest.getPollDateTime(), tweetRequest.getChoices(), tweet);
    }

    @Test
    void voteInPoll() {
        TweetResponse tweetResponse = new TweetResponse();
        VoteRequest voteRequest = new VoteRequest();
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        when(pollService.voteInPoll(voteRequest.getTweetId(), voteRequest.getPollId(), voteRequest.getPollChoiceId())).thenReturn(tweetProjection);
        when(basicMapper.convertToResponse(tweetProjection, TweetResponse.class)).thenReturn(tweetResponse);
        assertEquals(tweetResponse, pollMapper.voteInPoll(voteRequest));
        verify(pollService, times(1)).voteInPoll(voteRequest.getTweetId(), voteRequest.getPollId(), voteRequest.getPollChoiceId());
        verify(basicMapper, times(1)).convertToResponse(tweetProjection, TweetResponse.class);
    }
}
