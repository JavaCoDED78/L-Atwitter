package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.TopicTestHelper;
import com.gmail.javacoded78.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.enums.TopicCategory;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.TopicFollowers;
import com.gmail.javacoded78.model.TopicNotInterested;
import com.gmail.javacoded78.repository.TopicFollowersRepository;
import com.gmail.javacoded78.repository.TopicNotInterestedRepository;
import com.gmail.javacoded78.repository.TopicRepository;
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
import java.util.List;

import static com.gmail.javacoded78.constants.ErrorMessage.TOPIC_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
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
    private TopicFollowersRepository topicFollowersRepository;

    @Mock
    private TopicNotInterestedRepository topicNotInterestedRepository;

    @Mock
    private UserClient userClient;

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
        when(userClient.isUserExists(USER_ID)).thenReturn(true);
        when(topicRepository.getTopicsByTopicFollowerId(USER_ID, TopicProjection.class))
                .thenReturn(TopicTestHelper.getMockTopicProjectionList());
        List<TopicProjection> topics = topicService.getFollowedTopicsByUserId(USER_ID);
        assertThat(topics).hasSize(2);
        verify(topicRepository, times(1)).getTopicsByTopicFollowerId(USER_ID, TopicProjection.class);
        verify(userClient, times(1)).isUserExists(USER_ID);
    }

    @Test
    void getFollowedTopicsByUserId_shouldUserIdNotFound() {
        when(userClient.isUserExists(USER_ID)).thenReturn(false);
        validateProfileTest(USER_ID, String.format(USER_ID_NOT_FOUND, USER_ID), HttpStatus.NOT_FOUND);
        verify(userClient, times(1)).isUserExists(USER_ID);
    }

    @Test
    void getFollowedTopicsByUserId_shouldUserProfileBlocked() {
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        validateProfileTest(1L, USER_PROFILE_BLOCKED, HttpStatus.BAD_REQUEST);
        verify(userClient, times(1)).isUserExists(1L);
        verify(userClient, times(1)).isMyProfileBlockedByUser(1L);
    }

    @Test
    void getFollowedTopicsByUserId_shouldUserHavePrivateProfile() {
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(false);
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        validateProfileTest(1L, USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        verify(userClient, times(1)).isUserExists(1L);
        verify(userClient, times(1)).isMyProfileBlockedByUser(1L);
        verify(userClient, times(1)).isUserHavePrivateProfile(1L);
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
    void processNotInterestedTopic_deleteTopic() {
        when(topicRepository.isTopicExist(3L)).thenReturn(true);
        when(topicNotInterestedRepository.getNotInterestedByUserIdAndTopicId(USER_ID, 3L)).thenReturn(new TopicNotInterested());
        assertFalse(topicService.processNotInterestedTopic(3L));
        verify(topicRepository, times(1)).isTopicExist(3L);
        verify(topicNotInterestedRepository, times(1)).getNotInterestedByUserIdAndTopicId(USER_ID, 3L);
        verify(topicNotInterestedRepository, times(1)).delete(new TopicNotInterested());
    }

    @Test
    void processNotInterestedTopic_saveTopic() {
        when(topicRepository.isTopicExist(3L)).thenReturn(true);
        when(topicNotInterestedRepository.getNotInterestedByUserIdAndTopicId(USER_ID, 3L)).thenReturn(null);
        assertTrue(topicService.processNotInterestedTopic(3L));
        verify(topicRepository, times(1)).isTopicExist(3L);
        verify(topicNotInterestedRepository, times(1)).getNotInterestedByUserIdAndTopicId(USER_ID, 3L);
        verify(topicFollowersRepository, times(1)).removeFollowedTopic(USER_ID, 3L);
        verify(topicNotInterestedRepository, times(1)).save(TopicNotInterested.builder()
                .userId(USER_ID)
                .topicId(3L)
                .build());
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
    void processFollowTopic_deleteTopic() {
        when(topicRepository.isTopicExist(3L)).thenReturn(true);
        when(topicFollowersRepository.getFollowerByUserIdAndTopicId(USER_ID, 3L)).thenReturn(new TopicFollowers());
        assertFalse(topicService.processFollowTopic(3L));
        verify(topicRepository, times(1)).isTopicExist(3L);
        verify(topicFollowersRepository, times(1)).getFollowerByUserIdAndTopicId(USER_ID, 3L);
        verify(topicFollowersRepository, times(1)).delete(new TopicFollowers());
    }

    @Test
    void processFollowTopic_saveTopic() {
        when(topicRepository.isTopicExist(3L)).thenReturn(true);
        when(topicFollowersRepository.getFollowerByUserIdAndTopicId(USER_ID, 3L)).thenReturn(null);
        assertTrue(topicService.processFollowTopic(3L));
        verify(topicRepository, times(1)).isTopicExist(3L);
        verify(topicFollowersRepository, times(1)).getFollowerByUserIdAndTopicId(USER_ID, 3L);
        verify(topicNotInterestedRepository, times(1)).removeNotInterestedTopic(USER_ID, 3L);
        verify(topicFollowersRepository, times(1)).save(TopicFollowers.builder()
                .userId(USER_ID)
                .topicId(3L)
                .build());
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
}