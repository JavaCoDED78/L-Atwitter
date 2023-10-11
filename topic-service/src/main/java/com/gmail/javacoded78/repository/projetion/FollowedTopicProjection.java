package com.gmail.javacoded78.repository.projetion;

import com.gmail.javacoded78.enums.TopicCategory;
import org.springframework.beans.factory.annotation.Value;

public interface FollowedTopicProjection {

    Long getId();

    String getTopicName();
    TopicCategory getTopicCategory();

    @Value("#{true}")
    boolean getIsTopicFollowed();
}
