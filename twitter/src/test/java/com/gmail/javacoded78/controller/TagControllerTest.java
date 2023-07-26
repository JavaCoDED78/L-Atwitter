package com.gmail.javacoded78.controller;

import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.util.TestConstants;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yml")
@Sql(value = {"/sql/populate-table-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/populate-table-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(TestConstants.USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tags - Get all tags")
    public void getTags() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(TestConstants.URL_TAG_BASIC))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].tagName").isNotEmpty())
                .andExpect(jsonPath("$[*].tweetsQuantity").isNotEmpty());
    }

    @Test
    @WithUserDetails(TestConstants.USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tags/trends - Get trends")
    public void getTrends() throws Exception {
        mockMvc.perform(get(TestConstants.URL_TAG_BASIC + "/trends"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].tagName").isNotEmpty())
                .andExpect(jsonPath("$[*].tweetsQuantity").isNotEmpty());
    }

    @Test
    @WithUserDetails(TestConstants.USER_EMAIL)
    @DisplayName("[200] GET /api/v1/tags/search?tagName=#JetBrains - Get tweets by hashtag")
    public void getTweetsByTag() throws Exception {
        mockMvc.perform(get(TestConstants.URL_TAG_BASIC + "/search").param("tagName", "#JetBrains"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(43L))
                .andExpect(jsonPath("$[0].text").value(TestConstants.TWEET_TEXT))
                .andExpect(jsonPath("$[0].dateTime").value(TestConstants.TWEET_DATETIME))
                .andExpect(jsonPath("$[0].scheduledDate").isEmpty())
                .andExpect(jsonPath("$[0].addressedUsername").isEmpty())
                .andExpect(jsonPath("$[0].addressedId").isEmpty())
                .andExpect(jsonPath("$[0].addressedTweetId").isEmpty())
                .andExpect(jsonPath("$[0].replyType").value(ReplyType.EVERYONE.toString()))
                .andExpect(jsonPath("$[0].link").value(TestConstants.LINK))
                .andExpect(jsonPath("$[0].linkTitle").value(TestConstants.LINK_TITLE))
                .andExpect(jsonPath("$[0].linkDescription").value(TestConstants.LINK_DESCRIPTION))
                .andExpect(jsonPath("$[0].linkCover").value(TestConstants.LINK_COVER))
                .andExpect(jsonPath("$[0].linkCoverSize").value("LARGE"))
                .andExpect(jsonPath("$[0].user.id").value(2L))
                .andExpect(jsonPath("$[0].images").isEmpty())
                .andExpect(jsonPath("$[0].quoteTweet").isEmpty())
                .andExpect(jsonPath("$[0].poll").isEmpty())
                .andExpect(jsonPath("$[0].retweetsCount").value(0L))
                .andExpect(jsonPath("$[0].likedTweetsCount").value(0L))
                .andExpect(jsonPath("$[0].repliesCount").value(0L))
                .andExpect(jsonPath("$[0].isTweetLiked").value(false))
                .andExpect(jsonPath("$[0].isTweetRetweeted").value(false))
                .andExpect(jsonPath("$[0].isUserFollowByOtherUser").value(false))
                .andExpect(jsonPath("$[0].isTweetDeleted").value(false))
                .andExpect(jsonPath("$[0].isTweetBookmarked").value(false));
    }
}