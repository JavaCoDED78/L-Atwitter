package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.response.TopicResponse;
import com.gmail.javacoded78.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.repository.projection.FollowedTopicProjection;
import com.gmail.javacoded78.repository.projection.NotInterestedTopicProjection;
import com.gmail.javacoded78.repository.projection.TopicProjection;
import com.gmail.javacoded78.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TopicMapper {

    private final BasicMapper basicMapper;
    private final TopicService topicService;

    public List<TopicResponse> getTopicsByIds(List<Long> topicsIds) {
        List<TopicProjection> topics = topicService.getTopicsByIds(topicsIds);
        return basicMapper.convertToResponseList(topics, TopicResponse.class);
    }

    public List<TopicsByCategoriesResponse> getTopicsByCategories(List<TopicCategory> categories) {
        return topicService.getTopicsByCategories(categories);
    }

    public List<TopicResponse> getFollowedTopics() {
        List<FollowedTopicProjection> topics = topicService.getFollowedTopics();
        return basicMapper.convertToResponseList(topics, TopicResponse.class);
    }

    public List<TopicResponse> getFollowedTopicsByUserId(Long userId) {
        List<TopicProjection> topics = topicService.getFollowedTopicsByUserId(userId);
        return basicMapper.convertToResponseList(topics, TopicResponse.class);
    }

    public List<TopicResponse> getNotInterestedTopics() {
        List<NotInterestedTopicProjection> topics = topicService.getNotInterestedTopics();
        return basicMapper.convertToResponseList(topics, TopicResponse.class);
    }

    public Boolean processNotInterestedTopic(Long topicId) {
        return topicService.processNotInterestedTopic(topicId);
    }

    public Boolean processFollowTopic(Long topicId) {
        return topicService.processFollowTopic(topicId);
    }
}
