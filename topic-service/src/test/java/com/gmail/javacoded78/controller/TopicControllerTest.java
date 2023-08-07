package com.gmail.javacoded78.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.javacoded78.dto.request.SuggestedTopicsRequest;
import com.gmail.javacoded78.dto.request.TopicsCategoriesRequest;
import com.gmail.javacoded78.enums.TopicCategory;
import com.gmail.javacoded78.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = {"/sql-test/clear-topic-db.sql", "/sql-test/populate-topic-db.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/sql-test/clear-topic-db.sql"}, executionPhase = AFTER_TEST_METHOD)
public class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("[200] POST /ui/v1/topics/suggested - Get topics by ids")
    public void getTopicsByIds() throws Exception {
        SuggestedTopicsRequest topicsRequest = new SuggestedTopicsRequest();
        topicsRequest.setTopicsIds(Arrays.asList(1001L, 1002L, 1003L, 1004L, 1005L, 1006L, 1007L, 1008L, 1009L, 1010L));

        mockMvc.perform(post(UI_V1_TOPICS + SUGGESTED)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(topicsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(10)));
    }

    @Test
    @DisplayName("[200] POST /ui/v1/topics/category - Get topics by categories")
    public void getTopicsByCategories() throws Exception {
        TopicsCategoriesRequest topicsRequest = new TopicsCategoriesRequest();
        topicsRequest.setCategories(Arrays.asList(TopicCategory.ONLY_ON_TWITTER, TopicCategory.GAMING));

        mockMvc.perform(post(UI_V1_TOPICS + CATEGORY)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID)
                        .content(mapper.writeValueAsString(topicsRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].topicsByCategories[*]", hasSize(8)))
                .andExpect(jsonPath("$[0].topicsByCategories[0].topicCategory").value(TopicCategory.ONLY_ON_TWITTER.toString()))
                .andExpect(jsonPath("$[1].topicsByCategories[*]", hasSize(8)))
                .andExpect(jsonPath("$[1].topicsByCategories[0].topicCategory").value(TopicCategory.GAMING.toString()));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/followed - Get followed topics")
    public void getFollowedTopics() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOWED)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/followed/2 - Get followed topics by user id")
    public void getFollowedTopicsByUserId() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOWED_USER_ID, 2)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(4)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/topics/followed/3 - Should user have private profile")
    public void getFollowedTopicsByUserId_UserHavePrivateProfile() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOWED_USER_ID, 3)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(USER_NOT_FOUND)));
    }

    @Test
    @DisplayName("[404] GET /ui/v1/topics/followed/99 - Should return User not found")
    public void getFollowedTopicsByUserId_UserNotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOWED_USER_ID, 99)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(String.format(USER_ID_NOT_FOUND, 99))));
    }

    @Test
    @DisplayName("[400] GET /ui/v1/topics/followed/5 - Should User blocked")
    public void getFollowedTopicsByUserId_UserBlocked() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOWED_USER_ID, 5)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is(USER_PROFILE_BLOCKED)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/not_interested - Get not interested topics")
    public void getNotInterestedTopics() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + NOT_INTERESTED)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/not_interested/1001 - Add not interested topic")
    public void processNotInterestedTopic_addTopic() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + NOT_INTERESTED_TOPIC_ID, 1001)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/not_interested/1018 - Remove not interested topic")
    public void processNotInterestedTopic_removeTopic() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + NOT_INTERESTED_TOPIC_ID, 1018)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/not_interested/1 - Should topic not found")
    public void processNotInterestedTopic_NotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + NOT_INTERESTED_TOPIC_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TOPIC_NOT_FOUND)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/follow/1001 - Follow topic")
    public void processFollowTopic_followTopic() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOW_TOPIC_ID, 1001)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/follow/1008 - Unfollow topic")
    public void processFollowTopic_unfollowTopic() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOW_TOPIC_ID, 1008)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    @DisplayName("[200] GET /ui/v1/topics/follow/1 - Should topic not found")
    public void processFollowTopic_NotFound() throws Exception {
        mockMvc.perform(get(UI_V1_TOPICS + FOLLOW_TOPIC_ID, 1)
                        .header(AUTH_USER_ID_HEADER, TestConstants.USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is(TOPIC_NOT_FOUND)));
    }
}
