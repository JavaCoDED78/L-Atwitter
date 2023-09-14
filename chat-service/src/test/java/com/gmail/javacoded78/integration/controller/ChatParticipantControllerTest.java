package com.gmail.javacoded78.integration.controller;

import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_PARTICIPANT_NOT_FOUND;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.LEAVE_CHAT_ID;
import static com.gmail.javacoded78.constants.PathConstants.PARTICIPANT_CHAT_ID;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_CHAT;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class ChatParticipantControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /ui/v1/chat/participant/4/8 - Get chat participant")
    void getParticipant() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + PARTICIPANT_CHAT_ID, 4, 8)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.fullName").value(TestConstants.USERNAME2),
                        jsonPath("$.username").value(TestConstants.USERNAME2),
                        jsonPath("$.about").value(TestConstants.ABOUT2),
                        jsonPath("$.isPrivateProfile").value(false),
                        jsonPath("$.isMutedDirectMessages").value(true),
                        jsonPath("$.isUserBlocked").value(false),
                        jsonPath("$.isMyProfileBlocked").value(false),
                        jsonPath("$.isWaitingForApprove").value(false),
                        jsonPath("$.isFollower").value(true)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/chat/participant/4/11 - Chat not created")
    void getParticipant_ChatNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + PARTICIPANT_CHAT_ID, 4, 11)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(CHAT_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/chat/participant/5/8 - Participant Not Found in chat")
    void getParticipant_ParticipantNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + PARTICIPANT_CHAT_ID, 5, 8)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(CHAT_PARTICIPANT_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/chat/leave/3/8 - Leave from conversation")
     void leaveFromConversation() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + LEAVE_CHAT_ID, 3, 8)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Successfully left the chat")));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/chat/leave/5/10 - Leave from conversation and delete chat")
    void leaveFromConversationAndDeleteChat() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + LEAVE_CHAT_ID, 5, 10)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Chat successfully deleted")));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/chat/leave/10/10 - Participant not found")
    void leaveFromConversation_ParticipantNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + LEAVE_CHAT_ID, 10, 10)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(CHAT_PARTICIPANT_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/chat/leave/2/9 - Chat not found")
    void leaveFromConversation_ChatNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + LEAVE_CHAT_ID, 2, 9)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(CHAT_NOT_FOUND)));
    }
}