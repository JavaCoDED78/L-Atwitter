package com.gmail.javacoded78.integration.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.COUNT_TEXT;
import static com.gmail.javacoded78.constants.PathConstants.IDS;
import static com.gmail.javacoded78.constants.PathConstants.ID_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.USER_IDS;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class TweetApiControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Test
    @DisplayName("[200] POST /api/v1/tweets/ids - Get tweets by ids")
    void getTweetsByIds() throws Exception {
        mockMvc.perform(post(API_V1_TWEETS + IDS)
                        .content(mapper.writeValueAsString(new IdsRequest(List.of(40L, 41L, 42L))))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(3)));
    }

    @Test
    @DisplayName("[200] POST /api/v1/tweets/user/ids - Get tweets by user ids")
    void getTweetsByUserIds() throws Exception {
        mockMvc.perform(post(API_V1_TWEETS + USER_IDS)
                        .content(mapper.writeValueAsString(new IdsRequest(List.of(2L))))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[*]", hasSize(7)));
    }

    @Test
    @DisplayName("[200] GET /api/v1/tweets/43 - Get tweet by id")
    void getTweetById() throws Exception {
        mockMvc.perform(get(API_V1_TWEETS + TWEET_ID, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(43L),
                        jsonPath("$.text").value(TestConstants.TWEET_TEXT),
                        jsonPath("$.dateTime").value(TestConstants.TWEET_DATETIME),
                        jsonPath("$.scheduledDate").isEmpty(),
                        jsonPath("$.addressedUsername").isEmpty(),
                        jsonPath("$.addressedId").isEmpty(),
                        jsonPath("$.addressedTweetId").isEmpty(),
                        jsonPath("$.replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$.link").value(TestConstants.LINK),
                        jsonPath("$.linkTitle").value(TestConstants.LINK_TITLE),
                        jsonPath("$.linkDescription").value(TestConstants.LINK_DESCRIPTION),
                        jsonPath("$.linkCover").value(TestConstants.LINK_COVER),
                        jsonPath("$.linkCoverSize").value("LARGE"),
                        jsonPath("$.user.id").value(2L),
                        jsonPath("$.images").isEmpty(),
                        jsonPath("$.quoteTweet").isEmpty(),
                        jsonPath("$.poll").isEmpty(),
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
    @DisplayName("[200] GET /api/v1/tweets/notification/43 - Get notification tweet")
    public void getNotificationTweet() throws Exception {
        mockMvc.perform(get(API_V1_TWEETS + NOTIFICATION_TWEET_ID, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(43),
                        jsonPath("$.text").value(TestConstants.TWEET_TEXT),
                        jsonPath("$.authorId").value(2),
                        jsonPath("$.notificationCondition").value(false)
                );
    }

    @Test
    @DisplayName("[200] GET /api/v1/tweets/id/43 - Is tweet exists")
    void isTweetExists() throws Exception {
        mockMvc.perform(get(API_V1_TWEETS + ID_TWEET_ID, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName("[200] GET /api/v1/tweets/count/test - Get tweet count by text")
    void getTweetCountByText() throws Exception {
        mockMvc.perform(get(API_V1_TWEETS + COUNT_TEXT, "test")
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(4));
    }

    @Test
    @DisplayName("[200] GET /api/v1/tweets/chat/43 - Get chat tweet")
    void getChatTweet() throws Exception {
        mockMvc.perform(get(API_V1_TWEETS + CHAT_TWEET_ID, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(43L),
                        jsonPath("$.text").value(TestConstants.TWEET_TEXT),
                        jsonPath("$.dateTime").value(TestConstants.TWEET_DATETIME),
                        jsonPath("$.isDeleted").value(false),
                        jsonPath("$.user.id").value(TestConstants.USER_ID),
                        jsonPath("$.user.fullName").value(TestConstants.USERNAME),
                        jsonPath("$.user.username").value(TestConstants.USERNAME),
                        jsonPath("$.user.avatar").value(TestConstants.AVATAR_SRC_1)
                );
    }
}