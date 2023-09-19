package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.LikeTweet;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.LikeTweetRepository;
import com.gmail.javacoded78.repository.projection.LikeTweetProjection;
import com.gmail.javacoded78.service.LikeTweetService;
import com.gmail.javacoded78.service.util.TweetServiceHelper;
import com.gmail.javacoded78.service.util.TweetValidationHelper;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeTweetServiceImpl implements LikeTweetService {

    private final LikeTweetRepository likeTweetRepository;
    private final TweetServiceHelper tweetServiceHelper;
    private final TweetValidationHelper tweetValidationHelper;
    private final UserClient userClient;

    @Override
    public Page<LikeTweetProjection> getUserLikedTweets(Long userId, Pageable pageable) {
        tweetValidationHelper.validateUserProfile(userId);
        return likeTweetRepository.getUserLikedTweets(userId, pageable);
    }

    @Override
    public HeaderResponse<UserResponse> getLikedUsersByTweetId(Long tweetId, Pageable pageable) {
        tweetValidationHelper.checkValidTweet(tweetId);
        List<Long> likedUserIds = likeTweetRepository.getLikedUserIds(tweetId);
        return userClient.getUsersByIds(new IdsRequest(likedUserIds), pageable);
    }

    @Override
    @Transactional
    public NotificationResponse likeTweet(Long tweetId) {
        Tweet tweet = tweetValidationHelper.checkValidTweet(tweetId);
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        LikeTweet likedTweet = likeTweetRepository.getLikedTweet(authUserId, tweetId);
        boolean isTweetLiked;

        if (likedTweet != null) {
            likeTweetRepository.delete(likedTweet);
            userClient.updateLikeCount(false);
            isTweetLiked = false;
        } else {
            LikeTweet newLikeTweet = LikeTweet.builder()
                    .userId(authUserId)
                    .tweetId(tweetId)
                    .build();
            likeTweetRepository.save(newLikeTweet);
            userClient.updateLikeCount(true);
            isTweetLiked = true;
        }
        return tweetServiceHelper.sendNotification(NotificationType.LIKE, isTweetLiked, tweet.getAuthorId(), authUserId, tweetId);
    }
}
