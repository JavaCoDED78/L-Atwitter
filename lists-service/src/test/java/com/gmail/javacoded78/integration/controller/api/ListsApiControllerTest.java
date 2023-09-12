package com.gmail.javacoded78.integration.controller.api;

import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_LISTS;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.LIST_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_LIST_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class ListsApiControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /api/v1/lists/54 - Get Notification List")
    void getNotificationList() throws Exception {
        mockMvc.perform(get(API_V1_LISTS + LIST_ID, 54)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(54),
                        jsonPath("$.name").value(TestConstants.LIST_NAME)
                );
    }

    @Test
    @DisplayName("[200] GET /api/v1/lists/tweet/54 - Get tweet list by id")
    void getTweetList() throws Exception {
        mockMvc.perform(get(API_V1_LISTS + TWEET_LIST_ID, 54)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(54),
                        jsonPath("$.name").value(TestConstants.LIST_NAME),
                        jsonPath("$.altWallpaper").value(TestConstants.LIST_ALT_WALLPAPER),
                        jsonPath("$.wallpaper").isEmpty(),
                        jsonPath("$.listOwner.id").value(TestConstants.LIST_USER_ID),
                        jsonPath("$.membersSize").value(1L),
                        jsonPath("$.isPrivate").value(false)
                );
    }
}