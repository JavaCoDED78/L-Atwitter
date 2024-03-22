package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.TopicTestHelper;
import com.gmail.javacoded78.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.enums.TopicCategory;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.model.Topic;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.TopicRepository;
import com.gmail.javacoded78.repository.UserRepository;
import com.gmail.javacoded78.repository.projetion.FollowedTopicProjection;
import com.gmail.javacoded78.repository.projetion.NotInterestedTopicProjection;
import com.gmail.javacoded78.repository.projetion.TopicProjection;
import com.gmail.javacoded78.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.gmail.javacoded78.constants.ErrorMessage.TOPIC_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static com.gmail.javacoded78.util.TestConstants.TOPIC_ID;
import static com.gmail.javacoded78.util.TestConstants.TOPIC_NAME;
import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class TopicServiceImplTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TopicServiceImpl topicService;

    @BeforeEach
    void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    void getTags() {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(topicRepository.getTopicsByIds(ids)).thenReturn(TopicTestHelper.getMockTopicProjectionList());
        List<TopicProjection> topics = topicService.getTopicsByIds(ids);
        assertThat(topics).hasSize(2);
        verify(topicRepository, times(1)).getTopicsByIds(ids);
    }

    @Test
    void getTopicsByCategories() {
        List<TopicProjection> topicProjections = TopicTestHelper.getMockTopicProjectionList();
        when(topicRepository.getTopicsByCategory(TopicCategory.TRAVEL)).thenReturn(List.of(topicProjections.get(0)));
        when(topicRepository.getTopicsByCategory(TopicCategory.FOOD)).thenReturn(List.of(topicProjections.get(1)));
        List<TopicsByCategoriesResponse> topics = topicService.getTopicsByCategories(
                Arrays.asList(TopicCategory.TRAVEL, TopicCategory.FOOD));
        assertThat(topics).hasSize(2);
        assertEquals(TopicCategory.TRAVEL, topics.get(0).getTopicCategory());
        assertEquals(TopicCategory.FOOD, topics.get(1).getTopicCategory());
        verify(topicRepository, times(2)).getTopicsByCategory(any());
    }

    @Test
    void getFollowedTopics() {
        when(topicRepository.getTopicsByTopicFollowerId(USER_ID, FollowedTopicProjection.class))
                .thenReturn(TopicTestHelper.getMockFollowedTopicProjectionList());
        List<FollowedTopicProjection> topics = topicService.getFollowedTopics();
        assertThat(topics).hasSize(2);
    }

    @Test
    void getFollowedTopicsByUserId() {
        when(userRepository.isUserExists(USER_ID)).thenReturn(true);
        when(topicRepository.getTopicsByTopicFollowerId(USER_ID, TopicProjection.class))
                .thenReturn(TopicTestHelper.getMockTopicProjectionList());
        List<TopicProjection> topics = topicService.getFollowedTopicsByUserId(USER_ID);
        assertThat(topics).hasSize(2);
        verify(topicRepository, times(1)).getTopicsByTopicFollowerId(USER_ID, TopicProjection.class);
        verify(userRepository, times(1)).isUserExists(USER_ID);
    }

    @Test
    void getFollowedTopicsByUserId_shouldUserIdNotFound() {
        when(userRepository.isUserExists(USER_ID)).thenReturn(false);
        validateProfileTest(USER_ID, String.format(USER_ID_NOT_FOUND, USER_ID), HttpStatus.NOT_FOUND);
        verify(userRepository, times(1)).isUserExists(USER_ID);
    }

    @Test
    void getFollowedTopicsByUserId_shouldUserProfileBlocked() {
        when(userRepository.isUserExists(1L)).thenReturn(true);
        when(userRepository.isUserBlocked(1L, USER_ID)).thenReturn(true);
        validateProfileTest(1L, USER_PROFILE_BLOCKED, HttpStatus.BAD_REQUEST);
    }

    @Test
    void getFollowedTopicsByUserId_shouldUserHavePrivateProfile() {
        when(userRepository.isUserExists(1L)).thenReturn(true);
        when(userRepository.isUserBlocked(1L, USER_ID)).thenReturn(false);
        when(userRepository.isUserHavePrivateProfile(1L, USER_ID)).thenReturn(false);
        validateProfileTest(1L, USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Test
    void getNotInterestedTopics() {
        when(topicRepository.getTopicsByNotInterestedUserId(USER_ID))
                .thenReturn(TopicTestHelper.getMockNotInterestedTopicProjectionList());
        List<NotInterestedTopicProjection> topics = topicService.getNotInterestedTopics();
        assertThat(topics).hasSize(2);
        verify(topicRepository, times(1)).getTopicsByNotInterestedUserId(USER_ID);
    }

    @Test
    void processNotInterestedTopic_removeTopic() {
        User authUser = TopicTestHelper.mockAuthUser();
        Topic topic = mockTopic();
        topic.setTopicNotInterested(new HashSet<>(Set.of(authUser)));
        when(topicRepository.findById(TOPIC_ID)).thenReturn(Optional.of(topic));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(authUser));
        assertFalse(topicService.processNotInterestedTopic(TOPIC_ID));
        verify(topicRepository, times(1)).findById(TOPIC_ID);
        verify(userRepository, times(1)).findById(USER_ID);
    }

    @Test
    void processNotInterestedTopic_addTopic() {
        User authUser = TopicTestHelper.mockAuthUser();
        Topic topic = mockTopic();
        topic.setTopicNotInterested(new HashSet<>());
        when(topicRepository.findById(TOPIC_ID)).thenReturn(Optional.of(topic));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(authUser));
        assertTrue(topicService.processNotInterestedTopic(TOPIC_ID));
        verify(topicRepository, times(1)).findById(TOPIC_ID);
        verify(userRepository, times(1)).findById(USER_ID);
    }

    @Test
    void processNotInterestedTopic_topicNotFound() {
        when(topicRepository.isTopicExist(3L)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> topicService.processNotInterestedTopic(3L));
        assertThat(exception.getMessage()).isEqualTo(TOPIC_NOT_FOUND);
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(topicRepository, times(1)).isTopicExist(3L);
    }

    @Test
    void processFollowTopic_unfollowTopic() {
        User authUser = TopicTestHelper.mockAuthUser();
        Topic topic = mockTopic();
        topic.setTopicFollowers(new HashSet<>(Set.of(authUser)));
        when(topicRepository.findById(TOPIC_ID)).thenReturn(Optional.of(topic));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(authUser));
        assertFalse(topicService.processFollowTopic(TOPIC_ID));
        verify(topicRepository, times(1)).findById(TOPIC_ID);
        verify(userRepository, times(1)).findById(USER_ID);
    }

    @Test
    void processFollowTopic_followTopic() {
        User authUser = TopicTestHelper.mockAuthUser();
        Topic topic = mockTopic();
        topic.setTopicFollowers(new HashSet<>());
        when(topicRepository.findById(TOPIC_ID)).thenReturn(Optional.of(topic));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(authUser));
        assertTrue(topicService.processFollowTopic(TOPIC_ID));
        verify(topicRepository, times(1)).findById(TOPIC_ID);
        verify(userRepository, times(1)).findById(USER_ID);
    }

    @Test
    void processFollowTopic_topicNotFound() {
        when(topicRepository.isTopicExist(3L)).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> topicService.processFollowTopic(3L));
        assertThat(exception.getMessage()).isEqualTo(TOPIC_NOT_FOUND);
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(topicRepository, times(1)).isTopicExist(3L);
    }

    private void validateProfileTest(Long userId, String testMessage, HttpStatus status) {
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> topicService.getFollowedTopicsByUserId(userId));
        assertThat(exception.getMessage()).isEqualTo(testMessage);
        assertThat(exception.getStatus()).isEqualTo(status);
    }

    private Topic mockTopic() {
        Topic topic = new Topic();
        topic.setId(TOPIC_ID);
        topic.setTopicName(TOPIC_NAME);
        topic.setTopicCategory(TopicCategory.GAMING);
        return topic;
    }
}