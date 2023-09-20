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
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID_BOOKMARKED;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.USER_BOOKMARKS;
import static com.gmail.javacoded78.constants.PathConstants.USER_BOOKMARKS_TWEET_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class BookmarkControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/user/bookmarks - Get user bookmarks")
    void getUserBookmarks() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + USER_BOOKMARKS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(40L),
                        jsonPath("$[0].text").value("test tweet"),
                        jsonPath("$[0].dateTime").value("2023-09-20T20:29:03"),
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
                        jsonPath("$[0].quoteTweet").isEmpty(),
                        jsonPath("$[0].user.id").value(2L),
                        jsonPath("$[0].poll.id").value(2L),
                        jsonPath("$[*].images", hasSize(1)),
                        jsonPath("$[0].retweetsCount").value(1L),
                        jsonPath("$[0].likedTweetsCount").value(1L),
                        jsonPath("$[0].repliesCount").value(1L),
                        jsonPath("$[0].isTweetLiked").value(false),
                        jsonPath("$[0].isTweetRetweeted").value(false),
                        jsonPath("$[0].isUserFollowByOtherUser").value(false),
                        jsonPath("$[0].isTweetDeleted").value(false),
                        jsonPath("$[0].isTweetBookmarked").value(true)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/user/bookmarks/43 - Add tweet to bookmarks")
    void processUserBookmarks_addBookmark() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + USER_BOOKMARKS_TWEET_ID, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/user/bookmarks/40 - Remove tweet from bookmarks")
    void processUserBookmarks_removeBookmark() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + USER_BOOKMARKS_TWEET_ID, 40)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/tweets/user/bookmarks/99 - Should Tweet Not Found")
    void processUserBookmarks_ShouldTweetNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + USER_BOOKMARKS_TWEET_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/tweets/user/bookmarks/49 - Should Tweet deleted")
     void processUserBookmarks_ShouldTweetDeleted() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + USER_BOOKMARKS_TWEET_ID, 49)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(TWEET_DELETED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tweets/43/bookmarked - Get is tweet bookmarked")
    void getIsTweetBookmarked() throws Exception {
        mockMvc.perform(get(UI_V1_TWEETS + TWEET_ID_BOOKMARKED, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }
}