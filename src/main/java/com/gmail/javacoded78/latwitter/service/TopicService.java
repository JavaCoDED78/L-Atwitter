package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Topic;

import java.util.List;

public interface TopicService {

    List<Topic> getTopics();

    List<Topic> getTopicsByCategory(String topicCategory);

    List<Topic> getNotInterestedTopics();

    Boolean addNotInterestedTopic(Long topicId);

    Boolean processFollowTopic(Long topicId);
}
