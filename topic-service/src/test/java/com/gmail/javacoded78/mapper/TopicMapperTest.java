package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.TopicTestHelper;
import com.gmail.javacoded78.dto.response.TopicResponse;
import com.gmail.javacoded78.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.enums.TopicCategory;
import com.gmail.javacoded78.repository.projetion.FollowedTopicProjection;
import com.gmail.javacoded78.repository.projetion.NotInterestedTopicProjection;
import com.gmail.javacoded78.repository.projetion.TopicProjection;
import com.gmail.javacoded78.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class TopicMapperTest {

    @Mock
    private TopicService topicService;

    @Mock
    private BasicMapper basicMapper;

    @MockBean
    private ModelMapper modelMapper;
    @InjectMocks
    private TopicMapper topicMapper;
    @Test
    void getTopicsByIds() {
        List<TopicProjection> topics = TopicTestHelper.getMockTopicProjectionList();
        List<Long> topicsIds = Arrays.asList(1L, 2L);
        when(topicService.getTopicsByIds(topicsIds)).thenReturn(topics);
        when(basicMapper.convertToResponseList(topics, TopicResponse.class)).thenReturn(getTopicResponseList());
        assertThat(topicMapper.getTopicsByIds(topicsIds)).hasSize(2);
        verify(topicService, times(1)).getTopicsByIds(topicsIds);
        verify(basicMapper, times(1)).convertToResponseList(topics, TopicResponse.class);
    }

    @Test
    void getTopicsByCategories() {
        List<TopicProjection> topics = TopicTestHelper.getMockTopicProjectionList();
        List<TopicCategory> categories = Arrays.asList(TopicCategory.FOOD, TopicCategory.TRAVEL);
        TopicsByCategoriesResponse categoriesResponse1 = new TopicsByCategoriesResponse(
                TopicCategory.FOOD,
                Collections.singletonList(topics.get(0)));
        TopicsByCategoriesResponse categoriesResponse2 = new TopicsByCategoriesResponse(
                TopicCategory.TRAVEL,
                Collections.singletonList(topics.get(1)));
        when(topicService.getTopicsByCategories(categories))
                .thenReturn(Arrays.asList(categoriesResponse1, categoriesResponse2));
        assertThat(topicMapper.getTopicsByCategories(categories)).hasSize(2);
        verify(topicService, times(1)).getTopicsByCategories(categories);
    }

    @Test
    void getFollowedTopics() {
        List<FollowedTopicProjection> topics = TopicTestHelper.getMockFollowedTopicProjectionList();
        when(topicService.getFollowedTopics()).thenReturn(topics);
        when(basicMapper.convertToResponseList(topics, TopicResponse.class)).thenReturn(getTopicResponseList());
        assertThat(topicMapper.getFollowedTopics()).hasSize(2);
        verify(topicService, times(1)).getFollowedTopics();
        verify(basicMapper, times(1)).convertToResponseList(topics, TopicResponse.class);
    }

    @Test
    void getFollowedTopicsByUserId() {
        List<TopicProjection> topics = TopicTestHelper.getMockTopicProjectionList();
        when(topicService.getFollowedTopicsByUserId(1L)).thenReturn(topics);
        when(basicMapper.convertToResponseList(topics, TopicResponse.class)).thenReturn(getTopicResponseList());
        assertThat(topicMapper.getFollowedTopicsByUserId(1L)).hasSize(2);
        verify(topicService, times(1)).getFollowedTopicsByUserId(1L);
        verify(basicMapper, times(1)).convertToResponseList(topics, TopicResponse.class);
    }

    @Test
    void getNotInterestedTopics() {
        List<NotInterestedTopicProjection> topics = TopicTestHelper.getMockNotInterestedTopicProjectionList();
        when(topicService.getNotInterestedTopics()).thenReturn(topics);
        when(basicMapper.convertToResponseList(topics, TopicResponse.class)).thenReturn(getTopicResponseList());
        assertThat(topicMapper.getNotInterestedTopics()).hasSize(2);
        verify(topicService, times(1)).getNotInterestedTopics();
        verify(basicMapper, times(1)).convertToResponseList(topics, TopicResponse.class);
    }

    @Test
    void processNotInterestedTopic() {
        when(topicService.processNotInterestedTopic(1L)).thenReturn(true);
        assertTrue(topicMapper.processNotInterestedTopic(1L));
        verify(topicService, times(1)).processNotInterestedTopic(1L);
    }

    @Test
    void processFollowTopic() {
        when(topicService.processFollowTopic(1L)).thenReturn(true);
        assertTrue(topicMapper.processFollowTopic(1L));
        verify(topicService, times(1)).processFollowTopic(1L);
    }

    @Test
    void convertTopicProjectionToTopicResponse() {
        TopicProjection topic = TopicTestHelper.getMockTopicProjection();
        TopicResponse response = TopicResponse.builder()
                .id(1L)
                .topicName("test topic 1")
                .topicCategory(TopicCategory.TRAVEL)
                .isTopicFollowed(true)
                .isTopicNotInterested(false)
                .build();
        when(modelMapper.map(topic, TopicResponse.class)).thenReturn(response);
        TopicResponse topicResponse = modelMapper.map(topic, TopicResponse.class);
        assertEquals(topic.getId(), topicResponse.getId());
        assertEquals(topic.getTopicName(), topicResponse.getTopicName());
        assertEquals(topic.getTopicCategory(), topicResponse.getTopicCategory());
        assertTrue(topicResponse.isTopicFollowed());
        assertFalse(topicResponse.isTopicNotInterested());
    }

    private List<TopicResponse> getTopicResponseList() {
        TopicResponse topicResponse1 = TopicResponse.builder()
                .id(1L)
                .topicName("test topic 1")
                .topicCategory(TopicCategory.TRAVEL)
                .isTopicFollowed(false)
                .isTopicNotInterested(false)
                .build();
        TopicResponse topicResponse2 =TopicResponse.builder()
                .id(2L)
                .topicName("test topic 2")
                .topicCategory(TopicCategory.FOOD)
                .isTopicFollowed(false)
                .isTopicNotInterested(false)
                .build();
        return Arrays.asList(topicResponse1, topicResponse2);
    }
}