package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.latwitter.enums.TopicCategory;
import com.gmail.javacoded78.latwitter.exception.ApiRequestException;
import com.gmail.javacoded78.latwitter.model.Topic;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.TopicRepository;
import com.gmail.javacoded78.latwitter.repository.projection.TopicByCategoryProjection;
import com.gmail.javacoded78.latwitter.service.AuthenticationService;
import com.gmail.javacoded78.latwitter.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final AuthenticationService authenticationService;
    private final TopicRepository topicRepository;

    @Override
    public List<TopicByCategoryProjection> getTopicsByIds(List<Long> topicsIds) {
        return topicRepository.getTopicsByIds(topicsIds);
    }

    @Override
    public List<TopicsByCategoriesResponse> getTopicsByCategories(List<TopicCategory> categories) {
        List<TopicsByCategoriesResponse> topicsByCategories = new ArrayList<>();
        categories.forEach(topicCategory -> {
            TopicsByCategoriesResponse response = new TopicsByCategoriesResponse();
            response.setTopicCategory(topicCategory.toString());
            response.setTopicsByCategories(topicRepository.getTopicsByCategory(topicCategory));
            topicsByCategories.add(response);
        });
        return topicsByCategories;
    }

    @Override
    public List<Topic> getNotInterestedTopics() {
        User user = authenticationService.getAuthenticatedUser();
        return user.getNotInterestedTopics();
    }

    @Override
    @Transactional
    public Boolean processNotInterestedTopic(Long topicId) {
        checkIsTopicExist(topicId);
        Long userId = authenticationService.getAuthenticatedUserId();
        boolean isTopicNotInterested = topicRepository.isTopicNotInterested(userId, topicId);

        if (isTopicNotInterested) {
            topicRepository.removeNotInterestedTopic(userId, topicId);
            return false;
        } else {
            topicRepository.addNotInterestedTopic(userId, topicId);
            return true;
        }
    }

    @Override
    @Transactional
    public Boolean processFollowTopic(Long topicId) {
        checkIsTopicExist(topicId);
        Long userId = authenticationService.getAuthenticatedUserId();
        boolean isTopicFollowed = topicRepository.isTopicFollowed(userId, topicId);

        if (isTopicFollowed) {
            topicRepository.removeFollowedTopic(userId, topicId);
            return false;
        } else {
            topicRepository.addFollowedTopic(userId, topicId);
            return true;
        }
    }

    private void checkIsTopicExist(Long topicId) {
        boolean isTopicExist = topicRepository.isTopicExist(topicId);

        if (!isTopicExist) {
            throw new ApiRequestException("Topic not found", HttpStatus.NOT_FOUND);
        }
    }

    public boolean isTopicFollowed(Long topicId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return topicRepository.isTopicFollowed(authUserId, topicId);
    }

    public boolean isTopicNotInterested(Long topicId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return topicRepository.isTopicNotInterested(authUserId, topicId);
    }
}
