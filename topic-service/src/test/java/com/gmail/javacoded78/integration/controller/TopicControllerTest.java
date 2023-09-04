package com.gmail.javacoded78.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.SuggestedTopicsRequest;
import com.gmail.javacoded78.dto.request.TopicsCategoriesRequest;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.integration.IntegrationTestBase;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.gmail.javacoded78.constants.ErrorMessage.TOPIC_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.constants.PathConstants.CATEGORY;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOWED;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOWED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOW_TOPIC_ID;
import static com.gmail.javacoded78.constants.PathConstants.NOT_INTERESTED;
import static com.gmail.javacoded78.constants.PathConstants.NOT_INTERESTED_TOPIC_ID;
import static com.gmail.javacoded78.constants.PathConstants.SUGGESTED;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TOPICS;
import static com.gmail.javacoded78.enums.TopicCategory.GAMING;
import static com.gmail.javacoded78.enums.TopicCategory.ONLY_ON_TWITTER;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class TopicControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @MockBean
    private final UserClient userClient;

    @Test
    @DisplayName("[200] POST /ui/v1/topics/suggested - Get topics by ids")
    void getTopicsByIds() throws Exception {
        SuggestedTopicsRequest topicsRequest = new SuggestedTopicsRequest();
        topicsRequest.setTopicsIds(Arrays.asList(51L, 52L, 53L, 54L, 55L, 56L, 57L, 58L, 59L, 60L));

        mockMvc.perform(post(UI_V1_TOPICS + SUGGESTED)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(topicsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(10)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/topics/category - Get topics by categories")
    void getTopicsByCategories() throws Exception {
        TopicsCategoriesRequest topicsRequest = new TopicsCategoriesRequest();
        topicsRequest.setCategories(Arrays.asList(ONLY_ON_TWITTER, GAMING));

        mockMvc.perform(post(UI_V1_TOPICS + CATEGORY)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(topicsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[*]", hasSize(2)),
                        jsonPath("$[0].topicsByCategories[*]", hasSize(8)),
                        jsonPath("$[0].topicsByCategories[0].topicCategory").value(ONLY_ON_TWITTER.toString()),
                        jsonPath("$[0].topicsByCategories[0].topicCategory").value(ONLY_ON_TWITTER.toString()),
                        jsonPath("$[1].topicsByCategories[*]", hasSize(8)),
                        jsonPath("$[1].topicsByCategories[0].topicCategory").value(GAMING.toString()));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/followed - Get followed topics")
    void getFollowedTopics() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOWED)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/followed/2 - Get followed topics by user id")
    void getFollowedTopicsByUserId() throws Exception {
        when(userClient.isUserExists(any())).thenReturn(true);
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOWED_USER_ID, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/topics/followed/3 - Should user have private profile")
    void getFollowedTopicsByUserId_UserHavePrivateProfile() throws Exception {
        when(userClient.isUserExists(any())).thenReturn(true);
        when(userClient.isUserHavePrivateProfile(any())).thenReturn(true);
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOWED_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/topics/followed/99 - Should return User not found")
    void getFollowedTopicsByUserId_UserNotFound() throws Exception {
        when(userClient.isUserExists(any())).thenReturn(false);
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOWED_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/topics/followed/5 - Should User blocked")
    void getFollowedTopicsByUserId_UserBlocked() throws Exception {
        when(userClient.isUserExists(any())).thenReturn(true);
        when(userClient.isMyProfileBlockedByUser(any())).thenReturn(true);
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOWED_USER_ID, 5)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/not_interested - Get not interested topics")
    void getNotInterestedTopics() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + NOT_INTERESTED)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/not_interested/51 - Add not interested topic")
    void processNotInterestedTopic_addTopic() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + NOT_INTERESTED_TOPIC_ID, 51)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/not_interested/58 - Remove not interested topic")
    void processNotInterestedTopic_removeTopic() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + NOT_INTERESTED_TOPIC_ID, 58)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/not_interested/1 - Should topic not found")
    void processNotInterestedTopic_NotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + NOT_INTERESTED_TOPIC_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TOPIC_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/follow/51 - Follow topic")
    void processFollowTopic_followTopic() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOW_TOPIC_ID, 51)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/follow/58 - Unfollow topic")
    void processFollowTopic_unfollowTopic() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOW_TOPIC_ID, 58)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/follow/1 - Should topic not found")
    void processFollowTopic_NotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOW_TOPIC_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TOPIC_NOT_FOUND)));
    }
}
