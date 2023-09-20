package com.gmail.javacoded78.integration.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.TweetRequest;
import com.gmail.javacoded78.dto.request.VoteRequest;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_CHOICE_TEXT_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_POLL_CHOICES;
import static com.gmail.javacoded78.constants.ErrorMessage.POLL_CHOICE_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.POLL_NOT_FOUND;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.POLL;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.VOTE;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class PollControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Test
    @DisplayName("[200] POST /ui/v1/tweets/poll - Create tweet with poll")
    void createTweetWithPoll() throws Exception {
        List<String> pollChoiceList = new ArrayList<>();
        pollChoiceList.add("Choice 1");
        pollChoiceList.add("Choice 2");
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(TestConstants.TEST_TWEET_TEXT);
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setChoices(pollChoiceList);
        tweetRequest.setPollDateTime(100L);

        mockMvc.perform(post(UI_V1_TWEETS + POLL)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.text").value(TestConstants.TEST_TWEET_TEXT),
                        jsonPath("$.dateTime").isNotEmpty(),
                        jsonPath("$.scheduledDate").isEmpty(),
                        jsonPath("$.addressedUsername").isEmpty(),
                        jsonPath("$.addressedId").isEmpty(),
                        jsonPath("$.addressedTweetId").isEmpty(),
                        jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$.link").isEmpty(),
                        jsonPath("$.linkTitle").isEmpty(),
                        jsonPath("$.linkDescription").isEmpty(),
                        jsonPath("$.linkCover").isEmpty(),
                        jsonPath("$.linkCoverSize").isEmpty(),
                        jsonPath("$.quoteTweet").isEmpty(),
                        jsonPath("$.user.id").value(2L),
                        jsonPath("$.poll.id").isNotEmpty(),
                        jsonPath("$.poll.pollChoices[0].id").isNotEmpty(),
                        jsonPath("$.poll.pollChoices[1].id").isNotEmpty(),
                        jsonPath("$.images").isEmpty(),
                        jsonPath("$.retweetsCount").value(0L),
                        jsonPath("$.likedTweetsCount").value(0L),
                        jsonPath("$.repliesCount").value(0L),
                        jsonPath("$.isTweetLiked").value(false),
                        jsonPath("$.isTweetRetweeted").value(false),
                        jsonPath("$.isUserFollowByOtherUser").value(false),
                        jsonPath("$.isTweetDeleted").value(false),
                        jsonPath("$.isTweetBookmarked").value(false)
                );

    }

    @Test
    @DisplayName("[400] POST /ui/v1/tweets/poll - Should incorrect poll choices size is 1")
    void createTweetWithPoll_ShouldIncorrectPoolChoicesSizeIs1() throws Exception {
        List<String> pollChoiceList = new ArrayList<>();
        pollChoiceList.add("Choice 1");
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(TestConstants.TEST_TWEET_TEXT);
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setChoices(pollChoiceList);
        tweetRequest.setPollDateTime(100L);

        mockMvc.perform(post(UI_V1_TWEETS + POLL)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_POLL_CHOICES)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/tweets/poll - Should incorrect poll choices size is 5")
    void createTweetWithPoll_ShouldIncorrectPoolChoicesSizeIs5() throws Exception {
        List<String> pollChoiceList = new ArrayList<>();
        pollChoiceList.add("Choice 1");
        pollChoiceList.add("Choice 2");
        pollChoiceList.add("Choice 3");
        pollChoiceList.add("Choice 4");
        pollChoiceList.add("Choice 5");
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(TestConstants.TEST_TWEET_TEXT);
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setChoices(pollChoiceList);
        tweetRequest.setPollDateTime(100L);

        mockMvc.perform(post(UI_V1_TWEETS + POLL)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_POLL_CHOICES)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/tweets/poll - Should incorrect poll choices text length is 0")
    void createTweetWithPoll_ShouldIncorrectPoolChoicesTextLengthIs0() throws Exception {
        List<String> pollChoiceList = new ArrayList<>();
        pollChoiceList.add("Choice 1");
        pollChoiceList.add("");
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(TestConstants.TEST_TWEET_TEXT);
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setChoices(pollChoiceList);
        tweetRequest.setPollDateTime(100L);

        mockMvc.perform(post(UI_V1_TWEETS + POLL)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_CHOICE_TEXT_LENGTH)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/tweets/poll - Should incorrect poll choices text length more than 25")
    void createTweetWithPoll_ShouldIncorrectPoolChoicesTextLengthMoreThan25() throws Exception {
        List<String> pollChoiceList = new ArrayList<>();
        pollChoiceList.add("Choice 1");
        pollChoiceList.add(TestConstants.LINK_DESCRIPTION);
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText(TestConstants.TEST_TWEET_TEXT);
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setChoices(pollChoiceList);
        tweetRequest.setPollDateTime(100L);

        mockMvc.perform(post(UI_V1_TWEETS + POLL)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_CHOICE_TEXT_LENGTH)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/tweets/vote - Vote in poll")
    void voteInPoll() throws Exception {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTweetId(40L);
        voteRequest.setPollId(2L);
        voteRequest.setPollChoiceId(9L);

        mockMvc.perform(post(UI_V1_TWEETS + VOTE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(voteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(40),
                        jsonPath("$.text").value("test tweet"),
                        jsonPath("$.dateTime").value("2023-09-20T20:29:03"),
                        jsonPath("$.scheduledDate").isEmpty(),
                        jsonPath("$.addressedUsername").isEmpty(),
                        jsonPath("$.addressedId").isEmpty(),
                        jsonPath("$.addressedTweetId").isEmpty(),
                        jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$.link").isEmpty(),
                        jsonPath("$.linkTitle").isEmpty(),
                        jsonPath("$.linkDescription").isEmpty(),
                        jsonPath("$.linkCover").isEmpty(),
                        jsonPath("$.linkCoverSize").isEmpty(),
                        jsonPath("$.user.id").value(2L),
                        jsonPath("$.images").isEmpty(),
                        jsonPath("$.quoteTweet").isEmpty(),
                        jsonPath("$.poll.pollChoices[0].id").value(9),
                        jsonPath("$.poll.pollChoices[0].votedUser[0].id").value(2),
                        jsonPath("$.poll.pollChoices[1].id").value(10),
                        jsonPath("$.poll.pollChoices[1].votedUser[0].id").value(1),
                        jsonPath("$.retweetsCount").value(1L),
                        jsonPath("$.likedTweetsCount").value(1L),
                        jsonPath("$.repliesCount").value(1L),
                        jsonPath("$.isTweetLiked").value(false),
                        jsonPath("$.isTweetRetweeted").value(false),
                        jsonPath("$.isUserFollowByOtherUser").value(false),
                        jsonPath("$.isTweetDeleted").value(false),
                        jsonPath("$.isTweetBookmarked").value(true)
                );
    }

    @Test
    @DisplayName("[404] POST /ui/v1/tweets/vote - Should poll Not Found")
    void voteInPoll_ShouldPollNotFound() throws Exception {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTweetId(40L);
        voteRequest.setPollId(99L);
        voteRequest.setPollChoiceId(3L);

        mockMvc.perform(post(UI_V1_TWEETS + VOTE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(voteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(POLL_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/tweets/vote - Should poll choice Not Found")
    void voteInPoll_ShouldPollChoiceNotFound() throws Exception {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTweetId(40L);
        voteRequest.setPollId(2L);
        voteRequest.setPollChoiceId(99L);

        mockMvc.perform(post(UI_V1_TWEETS + VOTE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(voteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(POLL_CHOICE_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/tweets/vote - Should tweet Not Found")
    void voteInPoll_ShouldTweetNotFound() throws Exception {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTweetId(99L);
        voteRequest.setPollId(2L);
        voteRequest.setPollChoiceId(9L);

        mockMvc.perform(post(UI_V1_TWEETS + VOTE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(voteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(POLL_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] POST /ui/v1/tweets/vote - Should poll in tweet Not Found")
    void voteInPoll_ShouldPollInTweetNotFound() throws Exception {
        VoteRequest voteRequest = new VoteRequest();
        voteRequest.setTweetId(40L);
        voteRequest.setPollId(8L);
        voteRequest.setPollChoiceId(11L);

        mockMvc.perform(post(UI_V1_TWEETS + VOTE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(voteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(POLL_NOT_FOUND)));
    }
}
