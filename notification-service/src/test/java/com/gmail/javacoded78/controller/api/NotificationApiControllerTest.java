package com.gmail.javacoded78.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_NOTIFICATION;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.LIST;
import static com.gmail.javacoded78.constants.PathConstants.TWEET;
import static com.gmail.javacoded78.constants.PathConstants.USER;
import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = {"/sql-test/populate-notification-db.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/sql-test/clear-notification-db.sql"}, executionPhase = AFTER_TEST_METHOD)
public class NotificationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("[200] POST /api/v1/notification/list - Send list notification")
    public void sendListNotification() throws Exception {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationType(NotificationType.LISTS)
                .notificationCondition(true)
                .userId(2L)
                .notifiedUserId(1L)
                .listId(4L)
                .build();
        mockMvc.perform(post(API_V1_NOTIFICATION + LIST)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(notificationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] POST /api/v1/notification/user - Send user notification")
    public void sendUserNotification() throws Exception {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationType(NotificationType.FOLLOW)
                .userId(2L)
                .notifiedUserId(1L)
                .userToFollowId(1L)
                .build();
        mockMvc.perform(post(API_V1_NOTIFICATION + USER)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(notificationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("[200] POST /api/v1/notification/tweet - Send tweet notification")
    public void sendTweetNotification() throws Exception {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .notificationType(NotificationType.LIKE)
                .notificationCondition(true)
                .notifiedUserId(1L)
                .userId(2L)
                .tweetId(45L)
                .build();
        mockMvc.perform(post(API_V1_NOTIFICATION + TWEET)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(notificationRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}