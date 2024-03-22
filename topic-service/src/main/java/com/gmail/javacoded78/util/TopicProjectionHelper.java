package com.gmail.javacoded78.util;

import com.gmail.javacoded78.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopicProjectionHelper {

    private final TopicRepository topicRepository;

    public boolean isTopicFollowed(Long topicId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return topicRepository.isTopicFollowed(authUserId, topicId);
    }

    public boolean isTopicNotInterested(Long topicId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return topicRepository.isTopicNotInterested(authUserId, topicId);
    }
}
