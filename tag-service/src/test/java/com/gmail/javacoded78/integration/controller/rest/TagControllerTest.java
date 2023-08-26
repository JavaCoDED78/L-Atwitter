package com.gmail.javacoded78.integration.controller.rest;

import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.feign.TweetClient;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.integration.mocks.TweetClientMock;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH;
import static com.gmail.javacoded78.constants.PathConstants.TRENDS;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TAGS;
import static com.gmail.javacoded78.util.TestConstants.LINK;
import static com.gmail.javacoded78.util.TestConstants.LINK_COVER;
import static com.gmail.javacoded78.util.TestConstants.LINK_DESCRIPTION;
import static com.gmail.javacoded78.util.TestConstants.LINK_TITLE;
import static com.gmail.javacoded78.util.TestConstants.TWEET_DATETIME;
import static com.gmail.javacoded78.util.TestConstants.TWEET_TEXT;
import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class TagControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @MockBean
    private final TweetClient tweetClient;

    @Test
    @DisplayName("[200] GET /ui/v1/tags - Get all tags")
    void getTags() throws Exception {
        mockMvc.perform(get(UI_V1_TAGS)
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(2)),
                        jsonPath("$[*].id").isNotEmpty(),
                        jsonPath("$[*].tagName").isNotEmpty(),
                        jsonPath("$[*].tweetsQuantity").isNotEmpty()
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tags/trends - Get trends")
    void getTrends() throws Exception {
        mockMvc.perform(get(UI_V1_TAGS + TRENDS)
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(2)),
                        jsonPath("$[*].id").isNotEmpty(),
                        jsonPath("$[*].tagName").isNotEmpty(),
                        jsonPath("$[*].tweetsQuantity").isNotEmpty()
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/tags/search?tagName=#JetBrains - Get tweets by hashtag")
    void getTweetsByTag() throws Exception {
        TweetClientMock.setMockListTweetResponse(tweetClient);

        mockMvc.perform(get(UI_V1_TAGS + SEARCH)
                        .param("tagName", "#JetBrains")
                        .header(AUTH_USER_ID_HEADER, USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(43L),
                        jsonPath("$[0].text").value(TWEET_TEXT),
                        jsonPath("$[0].dateTime").value(TWEET_DATETIME),
                        jsonPath("$[0].scheduledDate").isEmpty(),
                        jsonPath("$[0].addressedUsername").isEmpty(),
                        jsonPath("$[0].addressedId").isEmpty(),
                        jsonPath("$[0].addressedTweetId").isEmpty(),
                        jsonPath("$[0].replyType").value(ReplyType.EVERYONE.toString()),
                        jsonPath("$[0].link").value(LINK),
                        jsonPath("$[0].linkTitle").value(LINK_TITLE),
                        jsonPath("$[0].linkDescription").value(LINK_DESCRIPTION),
                        jsonPath("$[0].linkCover").value(LINK_COVER),
                        jsonPath("$[0].linkCoverSize").value("LARGE"),
                        jsonPath("$[0].user.id").value(2L),
                        jsonPath("$[0].images").isEmpty(),
                        jsonPath("$[0].quoteTweet").isEmpty(),
                        jsonPath("$[0].poll").isEmpty(),
                        jsonPath("$[0].retweetsCount").value(0L),
                        jsonPath("$[0].likedTweetsCount").value(0L),
                        jsonPath("$[0].repliesCount").value(0L),
                        jsonPath("$[0].isTweetLiked").value(false),
                        jsonPath("$[0].isTweetRetweeted").value(false),
                        jsonPath("$[0].isUserFollowByOtherUser").value(false),
                        jsonPath("$[0].isTweetDeleted").value(false),
                        jsonPath("$[0].isTweetBookmarked").value(false)
                );
        verify(tweetClient).getTweetsByIds(any(IdsRequest.class));
    }
}
