package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.latwitter.enums.TopicCategory;
import com.gmail.javacoded78.latwitter.model.Topic;
import com.gmail.javacoded78.latwitter.repository.projection.TopicByCategoryProjection;

import java.util.List;

public interface TopicService {

    List<TopicByCategoryProjection> getTopicsByIds(List<Long> topicsIds);

    List<TopicsByCategoriesResponse> getTopicsByCategories(List<TopicCategory> categories);

    List<Topic> getNotInterestedTopics();

    Boolean processNotInterestedTopic(Long topicId);

    Boolean processFollowTopic(Long topicId);
}
