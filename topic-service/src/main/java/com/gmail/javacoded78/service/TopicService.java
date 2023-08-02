package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.enums.TopicCategory;
import com.gmail.javacoded78.repository.projetion.FollowedTopicProjection;
import com.gmail.javacoded78.repository.projetion.NotInterestedTopicProjection;
import com.gmail.javacoded78.repository.projetion.TopicProjection;

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