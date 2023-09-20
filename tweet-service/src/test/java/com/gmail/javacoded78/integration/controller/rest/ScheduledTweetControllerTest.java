package com.gmail.javacoded78.integration.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.TweetDeleteRequest;
import com.gmail.javacoded78.dto.request.TweetRequest;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static com.gmail.javacoded78.constants.ErrorMessage.INCORRECT_TWEET_TEXT_LENGTH;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.SCHEDULE;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TWEETS;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class ScheduledTweetControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/schedule - Get scheduled tweets")
    void getScheduledTweets() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + SCHEDULE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(39L),
                        jsonPath("$[0].text").value("test tweet"),
                        jsonPath("$[0].dateTime").value("2023-09-20T20:29:03"),
                        jsonPath("$[0].scheduledDate").value("3021-10-03T20:33:36"),
                        jsonPath("$[0].addressedUsername").isEmpty(),
                        jsonPath("$[0].addressedId").isEmpty(),
                        jsonPath("$[0].addressedTweetId").isEmpty(),
                        jsonPath("$[0].replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$[0].link").isEmpty(),
                        jsonPath("$[0].linkTitle").isEmpty(),
                        jsonPath("$[0].linkDescription").isEmpty(),
                        jsonPath("$[0].linkCover").isEmpty(),
                        jsonPath("$[0].linkCoverSize").isEmpty(),
                        jsonPath("$[0].quoteTweet").isEmpty(),
                        jsonPath("$[0].user.id").value(2L),
                        jsonPath("$[0].poll").isEmpty(),
                        jsonPath("$[0].images").isEmpty(),
                        jsonPath("$[0].retweetsCount").value(0L),
                        jsonPath("$[0].likedTweetsCount").value(0L),
                        jsonPath("$[0].repliesCount").value(0L),
                        jsonPath("$[0].isTweetLiked").value(false),
                        jsonPath("$[0].isTweetRetweeted").value(false),
                        jsonPath("$[0].isUserFollowByOtherUser").value(false),
                        jsonPath("$[0].isTweetDeleted").value(false),
                        jsonPath("$[0].isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[200] POST /ui/v1/tweets/schedule - Create Scheduled Tweet")
    void createScheduledTweet() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setText("test tweet");
        tweetRequest.setReplyType(ReplyType.EVERYONE);
        tweetRequest.setScheduledDate(LocalDateTime.parse(TestConstants.TWEET_SCHEDULED_DATETIME));

        mockMvc.perform(post(UI_V1_TWEETS + SCHEDULE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.text").value("test tweet"),
                        jsonPath("$.dateTime").isNotEmpty(),
                        jsonPath("$.scheduledDate").value(TestConstants.TWEET_SCHEDULED_DATETIME),
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
                        jsonPath("$.poll").isEmpty(),
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
    @DisplayName("[200] PUT /ui/v1/tweets/schedule - Update Scheduled Tweet")
    void updateScheduledTweet() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setId(39L);
        tweetRequest.setText("test tweet2");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(put(UI_V1_TWEETS + SCHEDULE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(39L),
                        jsonPath("$.text").value("test tweet2"),
                        jsonPath("$.dateTime").isNotEmpty(),
                        jsonPath("$.scheduledDate").value(TestConstants.TWEET_SCHEDULED_DATETIME),
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
                        jsonPath("$.poll").isEmpty(),
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
    @DisplayName("[400] PUT /ui/v1/tweets/schedule - Should tweet text length length is 0")
    void updateScheduledTweet_ShouldTweetTextLengthLengthIs0() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setId(39L);
        tweetRequest.setText("");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(put(UI_V1_TWEETS + SCHEDULE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_TWEET_TEXT_LENGTH)));
    }

    @Test
    @DisplayName("[400] PUT /ui/v1/tweets/schedule - Should tweet text length more than 280 symbols")
    void updateScheduledTweet_ShouldTweetTextLengthMoreThan280Symbols() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setId(39L);
        tweetRequest.setText(TestConstants.LINK_DESCRIPTION + TestConstants.LINK_DESCRIPTION);
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(put(UI_V1_TWEETS + SCHEDULE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(INCORRECT_TWEET_TEXT_LENGTH)));
    }

    @Test
    @DisplayName("[404] PUT /ui/v1/tweets/schedule - Should Tweet Not Found")
    void updateScheduledTweet_ShouldTweetNotFound() throws Exception {
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setId(99L);
        tweetRequest.setText("test tweet99");
        tweetRequest.setReplyType(ReplyType.EVERYONE);

        mockMvc.perform(put(UI_V1_TWEETS + SCHEDULE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] DELETE /ui/v1/tweets/schedule - Delete scheduled Tweets")
    void deleteScheduledTweets() throws Exception {
        TweetDeleteRequest tweetDeleteRequest = new TweetDeleteRequest();
        tweetDeleteRequest.setTweetsIds(List.of(42L));

        mockMvc.perform(delete(UI_V1_TWEETS + SCHEDULE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(tweetDeleteRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Scheduled tweets deleted.")));
    }
}