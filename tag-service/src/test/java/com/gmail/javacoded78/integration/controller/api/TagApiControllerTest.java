package com.gmail.javacoded78.integration.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.TweetTextRequest;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_TAGS;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.DELETE_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.PARSE_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_TEXT;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class TagApiControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Test
    @DisplayName("[200] GET /api/v1/tags/search/test - Get tags by text")
    void getTagsByText() throws Exception {
        mockMvc.perform(get(API_V1_TAGS + SEARCH_TEXT, "test")
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0]").value("#test")
                );
    }

    @Test
    @DisplayName("[200] GET /api/v1/tags/parse/99 - Parse new hashtag in text")
    void parseHashtagsInText_addNewHashtag() throws Exception {
        String hashtag = "#test_tag";
        mockMvc.perform(post(API_V1_TAGS + PARSE_TWEET_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(new TweetTextRequest(hashtag)))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] GET /api/v1/tags/parse/99 - Parse existing hashtags in text")
    void parseHashtagsInText_addExistingHashtag() throws Exception {
        String hashtag = "#test";
        mockMvc.perform(post(API_V1_TAGS + PARSE_TWEET_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(new TweetTextRequest(hashtag)))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] GET /api/v1/tags/delete/40 - Delete hashtag")
    void deleteTagsByTweetId() throws Exception {
        mockMvc.perform(delete(API_V1_TAGS + DELETE_TWEET_ID, 40)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] GET /api/v1/tags/delete/43 - Delete hashtag and update Tag Quantity")
    void deleteTagsByTweetId_updateTweetQuantity() throws Exception {
        mockMvc.perform(delete(API_V1_TAGS + DELETE_TWEET_ID, 43)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk());
    }
}
