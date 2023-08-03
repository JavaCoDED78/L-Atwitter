package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.enums.TopicCategory;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.TopicFollowers;
import com.gmail.javacoded78.model.TopicNotInterested;
import com.gmail.javacoded78.repository.TopicFollowersRepository;
import com.gmail.javacoded78.repository.TopicNotInterestedRepository;
import com.gmail.javacoded78.repository.TopicRepository;
import com.gmail.javacoded78.repository.projetion.FollowedTopicProjection;
import com.gmail.javacoded78.repository.projetion.NotInterestedTopicProjection;
import com.gmail.javacoded78.repository.projetion.TopicProjection;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicFollowersRepository topicFollowersRepository;
    private final TopicNotInterestedRepository topicNotInterestedRepository;
    private final UserClient userClient;

    @Override
    public List<TopicProjection> getTopicsByIds(List<Long> topicsIds) {
        return topicRepository.getTopicsByIds(topicsIds, TopicProjection.class);
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
    public List<FollowedTopicProjection> getFollowedTopics() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        List<Long> followedTopicsIds = topicFollowersRepository.getFollowedTopics(authUserId);
        return topicRepository.getTopicsByIds(followedTopicsIds, FollowedTopicProjection.class);
    }

    @Override
    public List<TopicProjection> getFollowedTopicsByUserId(Long userId) {
        validateUserProfile(userId);
        List<Long> followedTopicsIds = topicFollowersRepository.getFollowedTopics(userId);
        return topicRepository.getTopicsByIds(followedTopicsIds, TopicProjection.class);
    }

    @Override
    public List<NotInterestedTopicProjection> getNotInterestedTopics() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        List<Long> notInterestedTopicIds = topicNotInterestedRepository.getNotInterestedTopic(authUserId);
        return topicRepository.getTopicsByIds(notInterestedTopicIds, NotInterestedTopicProjection.class);
    }

    @Override
    @Transactional
    public Boolean processNotInterestedTopic(Long topicId) {
        checkIsTopicExist(topicId);
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        TopicNotInterested topic = topicNotInterestedRepository.getNotInterestedByUserIdAndTopicId(authUserId, topicId);

        if (topic != null) {
            topicNotInterestedRepository.delete(topic);
            return false;
        } else {
            topicFollowersRepository.removeFollowedTopic(authUserId, topicId);
            TopicNotInterested topicNotInterested = new TopicNotInterested(authUserId, topicId);
            topicNotInterestedRepository.save(topicNotInterested);
            return true;
        }
    }

    @Override
    @Transactional
    public Boolean processFollowTopic(Long topicId) {
        checkIsTopicExist(topicId);
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        TopicFollowers follower = topicFollowersRepository.getFollowerByUserIdAndTopicId(authUserId, topicId);

        if (follower != null) {
            topicFollowersRepository.delete(follower);
            return false;
        } else {
            topicNotInterestedRepository.removeNotInterestedTopic(authUserId, topicId);
            TopicFollowers topicFollowers = new TopicFollowers(authUserId, topicId);
            topicFollowersRepository.save(topicFollowers);
            return true;
        }
    }

    private void checkIsTopicExist(Long topicId) {
        boolean isTopicExist = topicRepository.isTopicExist(topicId);

        if (!isTopicExist) {
            throw new ApiRequestException("Topic not found", HttpStatus.NOT_FOUND);
        }
    }

    private void validateUserProfile(Long userId) {
        if (!userClient.isUserExists(userId)) {
            throw new ApiRequestException("User (id:" + userId + ") not found", HttpStatus.NOT_FOUND);
        }
        Long authUserId = AuthUtil.getAuthenticatedUserId();

        if (!userId.equals(authUserId)) {
            if (userClient.isMyProfileBlockedByUser(userId)) {
                throw new ApiRequestException("User profile blocked", HttpStatus.BAD_REQUEST);
            }
            if (!userClient.isUserHavePrivateProfile(userId)) {
                throw new ApiRequestException("User not found", HttpStatus.NOT_FOUND);
            }
        }
    }

    public boolean isTopicFollowed(Long topicId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return topicFollowersRepository.isTopicFollowed(authUserId, topicId);
    }

    public boolean isTopicNotInterested(Long topicId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return topicNotInterestedRepository.isTopicNotInterested(authUserId, topicId);
    }
}
