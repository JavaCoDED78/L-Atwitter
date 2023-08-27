package com.gmail.javacoded78.repository.projetion;

import org.springframework.beans.factory.annotation.Value;

public interface FollowedTopicProjection {

    Long getId();

    @Value("#{true}")
    boolean getIsTopicFollowed();
}
