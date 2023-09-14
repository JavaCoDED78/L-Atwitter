package com.gmail.javacoded78.integration.controller;

import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_PARTICIPANT_BLOCKED;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_ID;
import static com.gmail.javacoded78.constants.PathConstants.CREATE_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_CHAT;
import static com.gmail.javacoded78.constants.PathConstants.USERS;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class ChatControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /ui/v1/chat/1 - Get user chat")
    void getChatById() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + CHAT_ID, 8)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.creationDate").isNotEmpty(),
                        jsonPath("$.participants").isNotEmpty(),
                        jsonPath("$.participants[*]", hasSize(2)),
                        jsonPath("$.participants[0].user.id").value(2L),
                        jsonPath("$.participants[1].user.id").value(1L)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/chat/1 - Should chat Not Found")
    void getChatById_ShouldChatNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + CHAT_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(CHAT_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/chat/users - Get user chats")
    void getUserChats() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + USERS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(2)),
                        jsonPath("$[0].id").value(8L),
                        jsonPath("$[1].id").value(10L),
                        jsonPath("$[*].participants").isNotEmpty(),
                        jsonPath("$[0].participants[0].user.id").value(2L),
                        jsonPath("$[0].participants[1].user.id").value(1L)
                );
    }

    @Test
    @DisplayName("[200] GET /ui/v1/chat/create/3 - Create chat with participant")
    void createChat() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + CREATE_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.creationDate").isNotEmpty(),
                        jsonPath("$.participants").isNotEmpty(),
                        jsonPath("$.participants[*]", hasSize(2)),
                        jsonPath("$.participants[0].user.id").value(2L),
                        jsonPath("$.participants[1].user.id").value(3L)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/chat/create/99 - Should participant Not Found")
    void createChat_ShouldParticipantNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + CREATE_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/chat/create/6 - Bad Request. Create chat with blocked user")
    void createChat_BadRequest() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + CREATE_USER_ID, 6)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(CHAT_PARTICIPANT_BLOCKED)));
    }
}