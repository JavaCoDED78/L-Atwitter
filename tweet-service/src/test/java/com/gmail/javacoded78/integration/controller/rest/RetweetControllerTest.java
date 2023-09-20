package com.gmail.javacoded78.integration.controller.rest;

import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_DELETED;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.REPLIES_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.RETWEET_USER_ID_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID_RETWEETED_USERS;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TWEETS;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class RetweetControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/replies/user/2 - Get user retweets and replies by id")
    void getUserRetweetsAndReplies() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + REPLIES_USER_ID, TestConstants.USER_ID)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(45L),
                        jsonPath("$[0].text").value("media tweet test"),
                        jsonPath("$[0].dateTime").value("2023-09-20T20:38:51"),
                        jsonPath("$[0].scheduledDate").isEmpty(),
                        jsonPath("$[0].addressedUsername").isEmpty(),
                        jsonPath("$[0].addressedId").isEmpty(),
                        jsonPath("$[0].addressedTweetId").isEmpty(),
                        jsonPath("$[0].replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$[0].link").isEmpty(),
                        jsonPath("$[0].linkTitle").isEmpty(),
                        jsonPath("$[0].linkDescription").isEmpty(),
                        jsonPath("$[0].linkCover").isEmpty(),
                        jsonPath("$[0].linkCoverSize").isEmpty(),
                        jsonPath("$[0].quoteTweet.id").value(40L),
                        jsonPath("$[0].user.id").value(1L),
                        jsonPath("$[0].poll").isEmpty(),
                        jsonPath("$[*].images", hasSize(1)),
                        jsonPath("$[0].retweetsCount").value(1L),
                        jsonPath("$[0].likedTweetsCount").value(1L),
                        jsonPath("$[0].repliesCount").value(0L),
                        jsonPath("$[0].isTweetLiked").value(true),
                        jsonPath("$[0].isTweetRetweeted").value(true),
                        jsonPath("$[0].isUserFollowByOtherUser").value(true),
                        jsonPath("$[0].isTweetDeleted").value(false),
                        jsonPath("$[0].isTweetBookmarked").value(false)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/replies/user/99 - Should user Not Found by id")
    void getUserRetweetsAndReplies_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + REPLIES_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/replies/user/3 - Should user have private profile")
    void getUserRetweetsAndReplies_ShouldUserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + REPLIES_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/replies/user/6 - Should user blocked by other user")
    void getUserRetweetsAndReplies_ShouldUserBlockedByOtherUser() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + REPLIES_USER_ID, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/40/retweeted-users - Get Retweeted Users By Tweet Id")
    void getRetweetedUsersByTweetId() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_RETWEETED_USERS, 40)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(1L),
                        jsonPath("$[0].fullName").value(TestConstants.USERNAME2),
                        jsonPath("$[0].username").value(TestConstants.USERNAME2),
                        jsonPath("$[0].about").value(TestConstants.ABOUT2),
                        jsonPath("$[0].isPrivateProfile").value(false),
                        jsonPath("$[0].isMutedDirectMessages").value(true),
                        jsonPath("$[0].isUserBlocked").value(false),
                        jsonPath("$[0].isMyProfileBlocked").value(false),
                        jsonPath("$[0].isWaitingForApprove").value(false),
                        jsonPath("$[0].isFollower").value(true)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/99/retweeted-users - Should tweet Not Found by id")
    void getRetweetedUsersByTweetId_ShouldTweetNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_RETWEETED_USERS, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/49/retweeted-users - Should tweet deleted")
    void getRetweetedUsersByTweetId_ShouldTweetDeleted() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_RETWEETED_USERS, 49)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(TWEET_DELETED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/retweet/2/43 - Retweet tweet by id")
    void retweet() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + RETWEET_USER_ID_TWEET_ID, 2, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(43))
                .andExpect(jsonPath("$.text").value(TestConstants.TWEET_TEXT))
                .andExpect(jsonPath("$.authorId").value(2))
                .andExpect(jsonPath("$.notificationCondition").value(true));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/retweet/2/45 - UnRetweet tweet by id")
    void unretweet() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + RETWEET_USER_ID_TWEET_ID, 2, 45)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(45))
                .andExpect(jsonPath("$.text").value("media tweet test"))
                .andExpect(jsonPath("$.authorId").value(1))
                .andExpect(jsonPath("$.notificationCondition").value(false));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/retweet/2/99 - Should Tweet Not Found by id")
    void retweet_ShouldTweetNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + RETWEET_USER_ID_TWEET_ID, 2, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/retweet/2/49 - Should tweet deleted")
    void retweet_ShouldTweetDeleted() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + RETWEET_USER_ID_TWEET_ID, 2, 49)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(TWEET_DELETED)));
    }
}