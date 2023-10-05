package com.gmail.javacoded78.integration.controller.rest;

import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.MUTED;
import static com.gmail.javacoded78.constants.PathConstants.MUTED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_USER;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class MuteUserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /ui/v1/user/muted - Get muted list")
    void getMutedList() throws Exception {
        mockMvc.perform(get(UI_V1_USER + MUTED)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(1)),
                        jsonPath("$[0].id").value(1L),
                        jsonPath("$[0].fullName").value(TestConstants.USERNAME2),
                        jsonPath("$[0].username").value(TestConstants.USERNAME2),
                        jsonPath("$[0].about").value(TestConstants.ABOUT2),
                        jsonPath("$[0].avatar").value(TestConstants.AVATAR_SRC_2),
                        jsonPath("$[0].isPrivateProfile").value(false),
                        jsonPath("$[0].isUserMuted").value(true)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/muted/3 - Mute user by id")
    void addToMutedList() throws Exception {
        mockMvc.perform(get(UI_V1_USER + MUTED_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/user/muted/1 - Unmute user by id")
    void removeFromMutedList() throws Exception {
        mockMvc.perform(get(UI_V1_USER + MUTED_USER_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/user/muted/99 - Should user Not Found by id")
    void addToMutedList_ShouldUserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_USER + MUTED_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }
}
