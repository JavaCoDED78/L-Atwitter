package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.response.TopicResponse;
import com.gmail.javacoded78.latwitter.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.latwitter.enums.TopicCategory;
import com.gmail.javacoded78.latwitter.model.Topic;
import com.gmail.javacoded78.latwitter.repository.projection.TopicByCategoryProjection;
import com.gmail.javacoded78.latwitter.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TopicMapper {

    private final BasicMapper basicMapper;
    private final TopicService topicService;

    public List<TopicResponse> getTopicsByIds(List<Long> topicsIds) {
        List<TopicByCategoryProjection> topics = topicService.getTopicsByIds(topicsIds);
        return basicMapper.convertToResponseList(topics, TopicResponse.class);
    }

    public List<TopicsByCategoriesResponse> getTopicsByCategories(List<TopicCategory> categories) {
        return topicService.getTopicsByCategories(categories);
    }

    public List<TopicResponse> getNotInterestedTopics() {
        List<Topic> topics = topicService.getNotInterestedTopics();
        return basicMapper.convertToResponseList(topics, TopicResponse.class);
    }

    public Boolean processNotInterestedTopic(Long topicId) {
        return topicService.processNotInterestedTopic(topicId);
    }

    public Boolean processFollowTopic(Long topicId) {
        return topicService.processFollowTopic(topicId);
    }
}
