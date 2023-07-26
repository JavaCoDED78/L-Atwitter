package com.gmail.javacoded78.repository.projection;

import com.gmail.javacoded78.enums.TopicCategory;
import org.springframework.beans.factory.annotation.Value;

public interface NotInterestedTopicProjection {

    Long getId();
    String getTopicName();
    TopicCategory getTopicCategory();

    @Value("#{true}")
    boolean getIsTopicNotInterested();
}
