package com.gmail.javacoded78.service.impl;

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
import com.gmail.javacoded78.service.TopicService;
import com.gmail.javacoded78.service.UserService;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gmail.javacoded78.constants.ErrorMessage.TOPIC_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_ID_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicFollowersRepository topicFollowersRepository;
    private final TopicNotInterestedRepository topicNotInterestedRepository;
    private final UserClient userClient;
    private final UserService userService;

    @Override
    public List<TopicProjection> getTopicsByIds(List<Long> topicsIds) {
        return topicRepository.getTopicsByIds(topicsIds);
    }

    @Override
    public List<TopicsByCategoriesResponse> getTopicsByCategories(List<TopicCategory> categories) {
        return categories.stream()
                .map(category -> new TopicsByCategoriesResponse(
                        category,
                        topicRepository.getTopicsByCategory(category)
                ))
                .toList();
    }

    @Override
    public List<FollowedTopicProjection> getFollowedTopics() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return topicRepository.getTopicsByTopicFollowerId(authUserId, FollowedTopicProjection.class);
    }

    @Override
    public List<TopicProjection> getFollowedTopicsByUserId(Long userId) {
        validateUserProfile(userId);
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
        checkIsTopicExist(topicId);
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        TopicNotInterested topic = topicNotInterestedRepository.getNotInterestedByUserIdAndTopicId(authUserId, topicId);

        if (topic != null) {
            topicNotInterestedRepository.delete(topic);
            return false;
        } else {
            topicFollowersRepository.removeFollowedTopic(authUserId, topicId);
            TopicNotInterested topicNotInterested = TopicNotInterested.builder()
                    .userId(authUserId)
                    .topicId(topicId)
                    .build();
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
            TopicFollowers topicFollowers = TopicFollowers.builder()
                    .userId(authUserId)
                    .topicId(topicId)
                    .build();
            topicFollowersRepository.save(topicFollowers);
            return true;
        }
    }

    private void checkIsTopicExist(Long topicId) {
        if (!topicRepository.isTopicExist(topicId)) {
            throw new ApiRequestException(TOPIC_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    private void validateUserProfile(Long userId) {
        checkUserExists(userId);
        Long authUserId = AuthUtil.getAuthenticatedUserId();

        if (!userId.equals(authUserId)) {
            checkProfileIsBlocked(userId);
            checkIfUserHavePrivateProfile(userId);
        }
    }

    private void checkIfUserHavePrivateProfile(Long userId) {
        boolean userHavePrivateProfile = userClient.isUserHavePrivateProfile(userId);
        if (userHavePrivateProfile) {
            throw new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    private void checkProfileIsBlocked(Long userId) {
        boolean profileBlockedByUser = userClient.isMyProfileBlockedByUser(userId);
        if (profileBlockedByUser) {
            throw new ApiRequestException(USER_PROFILE_BLOCKED, HttpStatus.BAD_REQUEST);
        }
    }

    private void checkUserExists(Long userId) {
        boolean notUserExists =!userService.isUserExists(userId);
        if (notUserExists) {
            throw new ApiRequestException(String.format(USER_ID_NOT_FOUND, userId), HttpStatus.NOT_FOUND);
        }
    }
}
