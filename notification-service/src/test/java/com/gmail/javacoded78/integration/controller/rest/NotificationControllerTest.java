package com.gmail.javacoded78.integration.controller.rest;

import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.MENTIONS;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_ID;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBES;
import static com.gmail.javacoded78.constants.PathConstants.TIMELINE;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_NOTIFICATION;
import static com.gmail.javacoded78.constants.PathConstants.USER;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class NotificationControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /ui/v1/notification/user - Get user notifications")
    void getUserNotifications() throws Exception {
        mockMvc.perform(get(UI_V1_NOTIFICATION + USER)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(3)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/notification/mentions - Get user mentions notifications")
    void getUserMentionsNotifications() throws Exception {
        mockMvc.perform(get(UI_V1_NOTIFICATION + MENTIONS)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/notification/subscribes - Get tweet authors notifications")
    void getTweetAuthorsNotifications() throws Exception {
        mockMvc.perform(get(UI_V1_NOTIFICATION + SUBSCRIBES)
                        .header(AUTH_USER_ID_HEADER, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/notification/37 - Get user notification by id")
    void getUserNotificationById() throws Exception {
        mockMvc.perform(get(UI_V1_NOTIFICATION + NOTIFICATION_ID, 37)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(37))
                .andExpect(jsonPath("$.date").value("2023-09-04T20:31:44"))
                .andExpect(jsonPath("$.notificationType").value(NotificationType.LIKE.toString()));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/notification/99 - Should notification not found")
    void getUserNotificationById_ShouldNotificationNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_NOTIFICATION + NOTIFICATION_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Notification not found")));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/notification/timeline - Get notifications from tweet authors")
    void getNotificationsFromTweetAuthors() throws Exception {
        mockMvc.perform(get(UI_V1_NOTIFICATION + TIMELINE)
                        .header(AUTH_USER_ID_HEADER, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));
    }
}