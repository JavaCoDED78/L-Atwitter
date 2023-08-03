package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.Retweet;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.RetweetRepository;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.RetweetProjection;
import com.gmail.javacoded78.repository.projection.TweetUserProjection;
import com.gmail.javacoded78.service.RetweetService;
import com.gmail.javacoded78.util.AuthUtil;
import com.gmail.javacoded78.util.TweetServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetweetServiceImpl implements RetweetService {

    private final TweetRepository tweetRepository;
    private final TweetServiceHelper tweetServiceHelper;
    private final RetweetRepository retweetRepository;
    private final UserClient userClient;

    @Override
    public Page<TweetUserProjection> getUserRetweetsAndReplies(Long userId, Pageable pageable) {
        tweetServiceHelper.validateUserProfile(userId);
        List<TweetUserProjection> replies = tweetRepository.getRepliesByUserId(userId);
        List<RetweetProjection> retweets = retweetRepository.getRetweetsByUserId(userId);
        List<TweetUserProjection> userTweets = tweetServiceHelper.combineTweetsArrays(replies, retweets);
        return tweetServiceHelper.getPageableTweetProjectionList(pageable, userTweets, replies.size() + retweets.size());
    }

    @Override
    public HeaderResponse<UserResponse> getRetweetedUsersByTweetId(Long tweetId, Pageable pageable) {
        tweetServiceHelper.checkValidTweet(tweetId);
        List<Long> retweetedUserIds = retweetRepository.getRetweetedUserIds(tweetId);
        return userClient.getRetweetedUsersByIds(new IdsRequest(retweetedUserIds), pageable);
    }

    @Override
    @Transactional
    public NotificationResponse retweet(Long tweetId) {
        Tweet tweet = tweetServiceHelper.checkValidTweet(tweetId);
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        Retweet retweet = retweetRepository.isTweetRetweeted(authUserId, tweetId);
        boolean isRetweeted;

        if (retweet != null) {
            retweetRepository.delete(retweet);
            userClient.updateTweetCount(false);
            isRetweeted = false;
        } else {
            Retweet newRetweet = new Retweet(authUserId, tweetId);
            retweetRepository.save(newRetweet);
            userClient.updateTweetCount(true);
            isRetweeted = true;
        }
        return tweetServiceHelper.sendNotification(NotificationType.RETWEET, isRetweeted, tweet.getAuthorId(), authUserId, tweetId);
    }
}