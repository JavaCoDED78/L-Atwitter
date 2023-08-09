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
import com.gmail.javacoded78.service.TopicService;
import com.gmail.javacoded78.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.gmail.javacoded78.constants.ErrorMessage.TOPIC_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static com.gmail.javacoded78.constants.PathConstants.AUTH_USER_ID_HEADER;
import static com.gmail.javacoded78.util.TestConstants.USER_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TopicServiceImplTest {

    @Autowired
    private TopicService topicService;

    @MockBean
    private TopicRepository topicRepository;

    @MockBean
    private TopicFollowersRepository topicFollowersRepository;

    @MockBean
    private TopicNotInterestedRepository topicNotInterestedRepository;

    @MockBean
    private UserClient userClient;

    @Before
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
    }

    @Test
    public void getTags() {
        List<Long> longs = Arrays.asList(1L, 2L);
        when(topicRepository.getTopicsByIds(longs)).thenReturn(TopicTestHelper.getMockTopicProjectionList());
        List<TopicProjection> topics = topicService.getTopicsByIds(longs);
        assertEquals(2, topics.size());
        verify(topicRepository, times(1)).getTopicsByIds(longs);
    }

    @Test
    public void getTopicsByCategories() {
        List<TopicProjection> topicProjections = TopicTestHelper.getMockTopicProjectionList();
        when(topicRepository.getTopicsByCategory(TopicCategory.TRAVEL)).thenReturn(List.of(topicProjections.get(0)));
        when(topicRepository.getTopicsByCategory(TopicCategory.FOOD)).thenReturn(List.of(topicProjections.get(1)));
        List<TopicsByCategoriesResponse> topics = topicService.getTopicsByCategories(
                Arrays.asList(TopicCategory.TRAVEL, TopicCategory.FOOD));
        assertEquals(2, topics.size());
        assertEquals(TopicCategory.TRAVEL, topics.get(0).getTopicCategory());
        assertEquals(TopicCategory.FOOD, topics.get(1).getTopicCategory());
        verify(topicRepository, times(2)).getTopicsByCategory(any());
    }

    @Test
    public void getFollowedTopics() {
        when(topicRepository.getTopicsByTopicFollowerId(USER_ID, FollowedTopicProjection.class))
                .thenReturn(TopicTestHelper.getMockFollowedTopicProjectionList());
        List<FollowedTopicProjection> topics = topicService.getFollowedTopics();
        assertEquals(2, topics.size());
    }

    @Test
    public void getFollowedTopicsByUserId() {
        when(userClient.isUserExists(USER_ID)).thenReturn(true);
        when(topicRepository.getTopicsByTopicFollowerId(USER_ID, TopicProjection.class))
                .thenReturn(TopicTestHelper.getMockTopicProjectionList());
        List<TopicProjection> topics = topicService.getFollowedTopicsByUserId(USER_ID);
        assertEquals(2, topics.size());
        verify(topicRepository, times(1)).getTopicsByTopicFollowerId(USER_ID, TopicProjection.class);
        verify(userClient, times(1)).isUserExists(USER_ID);
    }

    @Test
    public void getFollowedTopicsByUserId_shouldUserIdNotFound() {
        when(userClient.isUserExists(USER_ID)).thenReturn(false);
        validateProfileTest(USER_ID, String.format(USER_ID_NOT_FOUND, USER_ID), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getFollowedTopicsByUserId_shouldUserProfileBlocked() {
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        validateProfileTest(1L, USER_PROFILE_BLOCKED, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getFollowedTopicsByUserId_shouldUserHavePrivateProfile() {
        when(userClient.isUserExists(1L)).thenReturn(true);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(false);
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        validateProfileTest(1L, USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Test
    public void getNotInterestedTopics() {
        when(topicRepository.getTopicsByNotInterestedUserId(USER_ID))
                .thenReturn(TopicTestHelper.getMockNotInterestedTopicProjectionList());
        List<NotInterestedTopicProjection> topics = topicService.getNotInterestedTopics();
        assertEquals(2, topics.size());
        verify(topicRepository, times(1)).getTopicsByNotInterestedUserId(USER_ID);
    }

    @Test
    public void processNotInterestedTopic_deleteTopic() {
        when(topicRepository.isTopicExist(3L)).thenReturn(true);
        when(topicNotInterestedRepository.getNotInterestedByUserIdAndTopicId(USER_ID, 3L)).thenReturn(new TopicNotInterested());
        assertFalse(topicService.processNotInterestedTopic(3L));
        verify(topicRepository, times(1)).isTopicExist(3L);
        verify(topicNotInterestedRepository, times(1)).getNotInterestedByUserIdAndTopicId(USER_ID, 3L);
        verify(topicNotInterestedRepository, times(1)).delete(new TopicNotInterested());
    }

    @Test
    public void processNotInterestedTopic_saveTopic() {
        when(topicRepository.isTopicExist(3L)).thenReturn(true);
        when(topicNotInterestedRepository.getNotInterestedByUserIdAndTopicId(USER_ID, 3L)).thenReturn(null);
        assertTrue(topicService.processNotInterestedTopic(3L));
        verify(topicRepository, times(1)).isTopicExist(3L);
        verify(topicNotInterestedRepository, times(1)).getNotInterestedByUserIdAndTopicId(USER_ID, 3L);
        verify(topicFollowersRepository, times(1)).removeFollowedTopic(USER_ID, 3L);
        verify(topicNotInterestedRepository, times(1)).save(new TopicNotInterested(USER_ID, 3L));
    }

    @Test
    public void processNotInterestedTopic_topicNotFound() {
        when(topicRepository.isTopicExist(3L)).thenReturn(false);
        try {
            topicService.processNotInterestedTopic(3L);
        } catch (ApiRequestException exception) {
            assertEquals(TOPIC_NOT_FOUND, exception.getMessage());
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        }
    }

    @Test
    public void processFollowTopic_deleteTopic() {
        when(topicRepository.isTopicExist(3L)).thenReturn(true);
        when(topicFollowersRepository.getFollowerByUserIdAndTopicId(USER_ID, 3L)).thenReturn(new TopicFollowers());
        assertFalse(topicService.processFollowTopic(3L));
        verify(topicRepository, times(1)).isTopicExist(3L);
        verify(topicFollowersRepository, times(1)).getFollowerByUserIdAndTopicId(USER_ID, 3L);
        verify(topicFollowersRepository, times(1)).delete(new TopicFollowers());
    }

    @Test
    public void processFollowTopic_saveTopic() {
        when(topicRepository.isTopicExist(3L)).thenReturn(true);
        when(topicFollowersRepository.getFollowerByUserIdAndTopicId(USER_ID, 3L)).thenReturn(null);
        assertTrue(topicService.processFollowTopic(3L));
        verify(topicRepository, times(1)).isTopicExist(3L);
        verify(topicFollowersRepository, times(1)).getFollowerByUserIdAndTopicId(USER_ID, 3L);
        verify(topicNotInterestedRepository, times(1)).removeNotInterestedTopic(USER_ID, 3L);
        verify(topicFollowersRepository, times(1)).save(new TopicFollowers(USER_ID, 3L));
    }

    @Test
    public void processFollowTopic_topicNotFound() {
        when(topicRepository.isTopicExist(3L)).thenReturn(false);
        try {
            topicService.processFollowTopic(3L);
        } catch (ApiRequestException exception) {
            assertEquals(TOPIC_NOT_FOUND, exception.getMessage());
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        }
    }

    private void validateProfileTest(Long userId, String testMessage, HttpStatus status) {
        try {
            topicService.getFollowedTopicsByUserId(userId);
        } catch (ApiRequestException exception) {
            assertEquals(testMessage, exception.getMessage());
            assertEquals(status, exception.getStatus());
        }
    }
}