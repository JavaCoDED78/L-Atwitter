package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.latwitter.enums.TopicCategory;
import com.gmail.javacoded78.latwitter.repository.projection.topic.FollowedTopicProjection;
import com.gmail.javacoded78.latwitter.repository.projection.topic.NotInterestedTopicProjection;
import com.gmail.javacoded78.latwitter.repository.projection.topic.TopicProjection;

import java.util.List;

public interface TopicService {

    List<TopicProjection> getTopicsByIds(List<Long> topicsIds);

    List<TopicsByCategoriesResponse> getTopicsByCategories(List<TopicCategory> categories);

    List<FollowedTopicProjection> getFollowedTopics();

    List<TopicProjection> getFollowedTopicsByUserId(Long userId);

    List<NotInterestedTopicProjection> getNotInterestedTopics();

    Boolean processNotInterestedTopic(Long topicId);

    Boolean processFollowTopic(Long topicId);
}
