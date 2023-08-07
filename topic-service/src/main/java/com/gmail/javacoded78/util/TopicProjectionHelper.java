package com.gmail.javacoded78.util;

import com.gmail.javacoded78.repository.TopicFollowersRepository;
import com.gmail.javacoded78.repository.TopicNotInterestedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopicProjectionHelper {

    private final TopicFollowersRepository topicFollowersRepository;
    private final TopicNotInterestedRepository topicNotInterestedRepository;

    public boolean isTopicFollowed(Long topicId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return topicFollowersRepository.isTopicFollowed(authUserId, topicId);
    }

    public boolean isTopicNotInterested(Long topicId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return topicNotInterestedRepository.isTopicNotInterested(authUserId, topicId);
    }
}
