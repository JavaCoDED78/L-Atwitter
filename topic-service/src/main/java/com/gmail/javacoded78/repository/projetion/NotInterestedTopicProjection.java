package com.gmail.javacoded78.repository.projetion;

import org.springframework.beans.factory.annotation.Value;

public interface NotInterestedTopicProjection {

    Long getId();
    @Value("#{true}")
    boolean getIsTopicNotInterested();
}
