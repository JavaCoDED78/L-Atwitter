package com.gmail.javacoded78.latwitter.repository.projection.topic;

import com.gmail.javacoded78.latwitter.enums.TopicCategory;
import org.springframework.beans.factory.annotation.Value;

public interface NotInterestedTopicProjection {

    Long getId();

    String getTopicName();

    TopicCategory getTopicCategory();

    @Value("#{true}")
    boolean getIsTopicNotInterested();
}
