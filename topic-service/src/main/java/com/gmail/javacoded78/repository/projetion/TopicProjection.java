package com.gmail.javacoded78.repository.projetion;

import com.gmail.javacoded78.enums.TopicCategory;
import org.springframework.beans.factory.annotation.Value;

public interface TopicProjection {

    Long getId();
    String getTopicName();
    TopicCategory getTopicCategory();

    @Value("#{@topicProjectionHelper.isTopicFollowed(target.id)}")
    boolean getIsTopicFollowed();

    @Value("#{@topicProjectionHelper.isTopicNotInterested(target.id)}")
    boolean getIsTopicNotInterested();
}
