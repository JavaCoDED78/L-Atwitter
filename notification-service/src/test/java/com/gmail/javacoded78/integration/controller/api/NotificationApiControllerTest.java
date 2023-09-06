package com.gmail.javacoded78.integration.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationTweetResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.feign.WebSocketClient;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.mapper.NotificationClientMapper;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_NOTIFICATION;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.MENTION;
import static com.gmail.javacoded78.constants.PathConstants.TWEET;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class NotificationApiControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;
    @MockBean
    private final NotificationClientMapper notificationClientMapper;
    @MockBean
    private final WebSocketClient webSocketClient;

    @Test
    @DisplayName("[200] POST /api/v1/notification - Send list notification")
    void sendListNotification() throws Exception {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationType(NotificationType.LISTS)
                .notificationCondition(true)
                .userId(2L)
                .notifiedUserId(1L)
                .listId(3L)
                .build();
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .id(100L)
                .build();
        when(notificationClientMapper.sendNotification(notificationRequest)).thenReturn(notificationResponse);
        doNothing().when(webSocketClient).send(anyString(), any());
        mockMvc.perform(post(API_V1_NOTIFICATION)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(notificationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(notificationClientMapper, times(1)).sendNotification(notificationRequest);
        verify(webSocketClient, times(1)).send(anyString(), any());
    }

    @Test
    @DisplayName("[200] POST /api/v1/notification/tweet - Send tweet notification")
    void sendTweetNotification() throws Exception {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationType(NotificationType.LIKE)
                .notificationCondition(true)
                .notifiedUserId(1L)
                .userId(2L)
                .tweetId(16L)
                .build();
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .id(100L)
                .tweet(NotificationTweetResponse.builder()
                        .authorId(100L)
                        .build())
                .build();
        when(notificationClientMapper.sendNotification(notificationRequest)).thenReturn(notificationResponse);
        doNothing().when(webSocketClient).send(anyString(), any());
        mockMvc.perform(post(API_V1_NOTIFICATION + TWEET)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(notificationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(notificationClientMapper, times(1)).sendNotification(notificationRequest);
        verify(webSocketClient, times(3)).send(anyString(), any());
    }

    @Test
    @DisplayName("[200] POST /api/v1/notification/mention - Send tweet mention notification")
    void sendTweetMentionNotification() throws Exception {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationType(NotificationType.MENTION)
                .notifiedUserId(1L)
                .userId(2L)
                .tweetId(16L)
                .build();
        doNothing().when(notificationClientMapper).sendTweetMentionNotification(notificationRequest);
        doNothing().when(webSocketClient).send(anyString(), any());
        mockMvc.perform(post(API_V1_NOTIFICATION + MENTION)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(notificationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(notificationClientMapper, times(1)).sendTweetMentionNotification(notificationRequest);
        verify(webSocketClient, times(1)).send(anyString(), any());
    }
}