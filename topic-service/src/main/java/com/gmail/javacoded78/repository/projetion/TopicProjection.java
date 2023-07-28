package com.gmail.javacoded78.repository.projetion;

import com.gmail.javacoded78.common.enums.TopicCategory;
import org.springframework.beans.factory.annotation.Value;

public interface TopicProjection {

    Long getId();

    String getTopicName();

    TopicCategory getTopicCategory();

    @Value("#{@topicServiceImpl.isTopicFollowed(target.id)}")
    boolean getIsTopicFollowed();

    @Value("#{@topicServiceImpl.isTopicNotInterested(target.id)}")
    boolean getIsTopicNotInterested();
}
