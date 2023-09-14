package com.gmail.javacoded78.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.ChatMessageRequest;
import com.gmail.javacoded78.dto.request.MessageWithTweetRequest;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.CHAT_PARTICIPANT_BLOCKED;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static com.gmail.javacoded78.constants.PathConstants.ADD_MESSAGE;
import static com.gmail.javacoded78.constants.PathConstants.ADD_MESSAGE_TWEET;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_ID_MESSAGES;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_ID_READ_MESSAGES;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_CHAT;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class ChatMessageControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @Test
    @DisplayName("[200] GET /ui/v1/chat/8/messages - Get chat messages by chat id")
    void getChatMessages() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + CHAT_ID_MESSAGES, 8)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpectAll(
                        status().isOk(),
                jsonPath("$[*]", hasSize(3)),
                jsonPath("$[0].id").value(5L),
                jsonPath("$[0].text").value("hello from Androsor"),
                jsonPath("$[0].date").value("2023-09-14T20:39:55"),
                jsonPath("$[0].authorId").value(2L),
                jsonPath("$[0].tweet.id").value(40L)
                );
    }

    @Test
    @DisplayName("[404] GET /ui/v1/chat/99/messages - Should chat not found")
    void getChatMessages_ShouldChatNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + CHAT_ID_MESSAGES, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(CHAT_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/chat/8/read/messages - Read chat messages by chat id")
    void readChatMessages() throws Exception {
        mockMvc.perform(get(UI_V1_CHAT + CHAT_ID_READ_MESSAGES, 8)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/chat/add/message - Add chat message")
    void addMessage() throws Exception {
        ChatMessageRequest request = new ChatMessageRequest(8L, TestConstants.TEST_TWEET_TEXT);
        mockMvc.perform(post(UI_V1_CHAT + ADD_MESSAGE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[404] POST /ui/v1/chat/add/message - Chat Not Found")
    void addMessage_ChatNotFound() throws Exception {
        ChatMessageRequest request = new ChatMessageRequest(9L, TestConstants.TEST_TWEET_TEXT);
        mockMvc.perform(post(UI_V1_CHAT + ADD_MESSAGE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(CHAT_NOT_FOUND)));
    }

    @Test
    @DisplayName("[400] POST /ui/v1/chat/add/message - Chat Participant Is Blocked")
    void addMessage_ChatParticipantIsBlocked() throws Exception {
        ChatMessageRequest request = new ChatMessageRequest(10L, TestConstants.TEST_TWEET_TEXT);
        mockMvc.perform(post(UI_V1_CHAT + ADD_MESSAGE)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(CHAT_PARTICIPANT_BLOCKED)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/chat/add/message/tweet - Add message with Tweet")
    void addMessageWithTweet() throws Exception {
        MessageWithTweetRequest request = new MessageWithTweetRequest(TestConstants.TEST_TWEET_TEXT, 40L, Collections.singletonList(2L));
        mockMvc.perform(post(UI_V1_CHAT + ADD_MESSAGE_TWEET)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[404] POST /ui/v1/chat/add/message/tweet - Should tweet not found")
    void addMessageWithTweet_ShouldTweetNotFound() throws Exception {
        MessageWithTweetRequest request = new MessageWithTweetRequest(TestConstants.TEST_TWEET_TEXT, 99L, Collections.singletonList(2L));
        mockMvc.perform(post(UI_V1_CHAT + ADD_MESSAGE_TWEET)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TWEET_NOT_FOUND)));
    }
}