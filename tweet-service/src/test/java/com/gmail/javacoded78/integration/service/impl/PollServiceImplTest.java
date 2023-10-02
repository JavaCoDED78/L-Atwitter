package com.gmail.javacoded78.integration.service.impl;

import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.integration.service.TweetServiceTestHelper;
import com.gmail.javacoded78.model.Poll;
import com.gmail.javacoded78.model.PollChoice;
import com.gmail.javacoded78.model.PollChoiceVoted;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.PollChoiceRepository;
import com.gmail.javacoded78.repository.PollChoiceVotedRepository;
import com.gmail.javacoded78.repository.PollRepository;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.service.impl.PollServiceImpl;
import com.gmail.javacoded78.service.impl.TweetServiceImpl;
import com.gmail.javacoded78.service.util.TweetServiceHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_CHOICE_TEXT_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_POLL_CHOICES;
import static com.gmail.javacoded78.constants.ErrorMessage.POLL_IS_NOT_AVAILABLE;
import static com.gmail.javacoded78.constants.ErrorMessage.POLL_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_VOTED_IN_POLL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
class PollServiceImplTest extends AbstractAuthTest {

    private final PollServiceImpl pollService;

    @MockBean
    private final PollRepository pollRepository;

    @MockBean
    private final PollChoiceRepository pollChoiceRepository;

    @MockBean
    private final PollChoiceVotedRepository pollChoiceVotedRepository;

    @MockBean
    private final TweetServiceImpl tweetService;

    @MockBean
    private final TweetServiceHelper tweetServiceHelper;

    @MockBean
    private final TweetRepository tweetRepository;

    @MockBean
    private final UserClient userClient;

    private static Tweet tweet;

    @BeforeEach
    public void setUp() {
        super.setUp();
        tweet = new Tweet();
        tweet.setAuthorId(TestConstants.USER_ID);
    }

    @Test
    void createPoll() {
        PollChoice pollChoice1 = PollChoice.builder()
                .choice(TestConstants.POLL_CHOICE_1)
                .build();
        PollChoice pollChoice2 = PollChoice.builder()
                .choice(TestConstants.POLL_CHOICE_2)
                .build();

        List<String> choices = Arrays.asList(TestConstants.POLL_CHOICE_1, TestConstants.POLL_CHOICE_2);
        List<PollChoice> pollChoices = Arrays.asList(pollChoice1, pollChoice2);
        when(tweetServiceHelper.createTweet(new Tweet())).thenReturn(new TweetResponse());
        assertEquals(new TweetResponse(), pollService.createPoll(123L, choices, new Tweet()));
        verify(pollChoiceRepository, times(2)).save(PollChoice.builder()
                .choice(TestConstants.POLL_CHOICE_1)
                .build());
        verify(pollChoiceRepository, times(2)).save(PollChoice.builder()
                .choice(TestConstants.POLL_CHOICE_2)
                .build());
        verify(pollRepository, times(1)).save(Poll.builder()
                .dateTime(LocalDateTime.now().plusMinutes(123L))
                .tweet(new Tweet())
                .pollChoices(pollChoices)
                .build());
        verify(tweetServiceHelper, times(1)).createTweet(new Tweet());
    }

    @Test
    void createPoll_ShouldIncorrectPollChoices() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> pollService.createPoll(123L, List.of(TestConstants.POLL_CHOICE_1), new Tweet()));
        assertEquals(INCORRECT_POLL_CHOICES, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void createPoll_ShouldIncorrectPollChoiceTextLength() {
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> pollService.createPoll(123L, Arrays.asList("", TestConstants.POLL_CHOICE_2), new Tweet()));
        assertEquals(INCORRECT_CHOICE_TEXT_LENGTH, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void voteInPoll() {
        Poll poll = new Poll();
        poll.setDateTime(LocalDateTime.now().plusMinutes(Integer.MAX_VALUE));
        TweetProjection tweetProjection = TweetServiceTestHelper.createTweetProjection(false, TweetProjection.class);
        when(tweetRepository.getTweetByPollIdAndTweetId(TestConstants.TWEET_ID, TestConstants.POLL_ID)).thenReturn(Optional.of(tweet));
        when(pollRepository.getPollByPollChoiceId(TestConstants.POLL_ID, TestConstants.POLL_CHOICE_ID)).thenReturn(Optional.of(poll));
        when(pollChoiceVotedRepository.ifUserVoted(TestConstants.USER_ID, TestConstants.POLL_CHOICE_ID)).thenReturn(false);
        when(tweetService.getTweetById(TestConstants.TWEET_ID)).thenReturn(tweetProjection);
        assertEquals(tweetProjection, pollService.voteInPoll(TestConstants.TWEET_ID, TestConstants.POLL_ID, TestConstants.POLL_CHOICE_ID));
        verify(tweetRepository, times(1)).getTweetByPollIdAndTweetId(TestConstants.TWEET_ID, TestConstants.POLL_ID);
        verify(pollRepository, times(1)).getPollByPollChoiceId(TestConstants.POLL_ID, TestConstants.POLL_CHOICE_ID);
        verify(pollChoiceVotedRepository, times(1)).ifUserVoted(TestConstants.USER_ID, TestConstants.POLL_CHOICE_ID);
        verify(pollChoiceVotedRepository, times(1)).save(PollChoiceVoted.builder()
                .votedUserId(TestConstants.USER_ID)
                .pollChoiceId(TestConstants.POLL_CHOICE_ID)
                .build());
        verify(tweetService, times(1)).getTweetById(TestConstants.TWEET_ID);
    }

    @Test
    void voteInPoll_ShouldPollNotFound() {
        when(tweetRepository.getTweetByPollIdAndTweetId(TestConstants.TWEET_ID, TestConstants.POLL_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> pollService.voteInPoll(TestConstants.TWEET_ID, TestConstants.POLL_ID, TestConstants.POLL_CHOICE_ID));
        assertEquals(POLL_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void voteInPoll_ShouldUserNotFound() {
        tweet.setAuthorId(1L);
        when(tweetRepository.getTweetByPollIdAndTweetId(TestConstants.TWEET_ID, TestConstants.POLL_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> pollService.voteInPoll(TestConstants.TWEET_ID, TestConstants.POLL_ID, TestConstants.POLL_CHOICE_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void voteInPoll_ShouldUserProfileBlocked() {
        tweet.setAuthorId(1L);
        when(tweetRepository.getTweetByPollIdAndTweetId(TestConstants.TWEET_ID, TestConstants.POLL_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> pollService.voteInPoll(TestConstants.TWEET_ID, TestConstants.POLL_ID, TestConstants.POLL_CHOICE_ID));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void voteInPoll_ShouldPollIsNotAvailable() {
        Poll poll = new Poll();
        poll.setDateTime(LocalDateTime.now());
        when(tweetRepository.getTweetByPollIdAndTweetId(TestConstants.TWEET_ID, TestConstants.POLL_ID)).thenReturn(Optional.of(tweet));
        when(pollRepository.getPollByPollChoiceId(TestConstants.POLL_ID, TestConstants.POLL_CHOICE_ID)).thenReturn(Optional.of(poll));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> pollService.voteInPoll(TestConstants.TWEET_ID, TestConstants.POLL_ID, TestConstants.POLL_CHOICE_ID));
        assertEquals(POLL_IS_NOT_AVAILABLE, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void voteInPoll_ShouldUserVotedInPoll() {
        Poll poll = new Poll();
        poll.setDateTime(LocalDateTime.now().plusMinutes(Integer.MAX_VALUE));
        when(tweetRepository.getTweetByPollIdAndTweetId(TestConstants.TWEET_ID, TestConstants.POLL_ID)).thenReturn(Optional.of(tweet));
        when(pollRepository.getPollByPollChoiceId(TestConstants.POLL_ID, TestConstants.POLL_CHOICE_ID)).thenReturn(Optional.of(poll));
        when(pollChoiceVotedRepository.ifUserVoted(TestConstants.USER_ID, TestConstants.POLL_CHOICE_ID)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> pollService.voteInPoll(TestConstants.TWEET_ID, TestConstants.POLL_ID, TestConstants.POLL_CHOICE_ID));
        assertEquals(USER_VOTED_IN_POLL, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
