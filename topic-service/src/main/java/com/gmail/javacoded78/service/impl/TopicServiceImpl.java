package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.enums.TopicCategory;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.model.Topic;
import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.TopicRepository;
import com.gmail.javacoded78.repository.projetion.FollowedTopicProjection;
import com.gmail.javacoded78.repository.projetion.NotInterestedTopicProjection;
import com.gmail.javacoded78.repository.projetion.TopicProjection;
import com.gmail.javacoded78.service.TopicService;
import com.gmail.javacoded78.service.UserService;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gmail.javacoded78.constants.ErrorMessage.TOPIC_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final UserService userService;

    @Override
    public List<TopicProjection> getTopicsByIds(List<Long> topicsIds) {
        return topicRepository.getTopicsByIds(topicsIds);
    }

    @Override
    public List<TopicsByCategoriesResponse> getTopicsByCategories(List<TopicCategory> categories) {
        return categories.stream()
                .map(topicCategory -> {
                    List<TopicProjection> topics = topicRepository.getTopicsByCategory(topicCategory);
                    return new TopicsByCategoriesResponse(topicCategory, topics);
                })
                .toList();
    }

    @Override
    public List<FollowedTopicProjection> getFollowedTopics() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return topicRepository.getTopicsByTopicFollowerId(authUserId, FollowedTopicProjection.class);
    }

    @Override
    public List<TopicProjection> getFollowedTopicsByUserId(Long userId) {
        userService.validateUserProfile(userId);
        return topicRepository.getTopicsByTopicFollowerId(userId, TopicProjection.class);
    }

    @Override
    public List<NotInterestedTopicProjection> getNotInterestedTopics() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return topicRepository.getTopicsByNotInterestedUserId(authUserId);
    }

    @Override
    @Transactional
    public Boolean processNotInterestedTopic(Long topicId) {
        Topic topic = getTopicById(topicId);
        User authUser = userService.getAuthUser();

        if (topic.getTopicNotInterested().contains(authUser)) {
            topic.getTopicNotInterested().remove(authUser);
            return false;
        } else {
            topic.getTopicFollowers().remove(authUser);
            topic.getTopicNotInterested().add(authUser);
            return true;
        }
    }

    @Override
    @Transactional
    public Boolean processFollowTopic(Long topicId) {
        Topic topic = getTopicById(topicId);
        User authUser = userService.getAuthUser();

        if (topic.getTopicFollowers().contains(authUser)) {
            topic.getTopicFollowers().remove(authUser);
            return false;
        } else {
            topic.getTopicNotInterested().remove(authUser);
            topic.getTopicFollowers().add(authUser);
            return true;
        }
    }

    private Topic getTopicById(Long topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new ApiRequestException(TOPIC_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
