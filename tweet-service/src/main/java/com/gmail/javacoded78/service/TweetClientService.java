package com.gmail.javacoded78.service;


import com.gmail.javacoded78.dto.ChatTweetResponse;
import com.gmail.javacoded78.dto.notification.NotificationTweetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TweetClientService {

//    Optional<Tweet> getTweetById(Long userId);
//
//    List<TweetsUserProjection> getTweetsByUserId(Long userId);
//
//    Optional<TweetsUserProjection> getPinnedTweetByUserId(Long userId);
//
//    Page<TweetProjection> getAllUserMediaTweets(TweetPageableRequest request);
//
//    Page<TweetProjection> getUserMentions(TweetPageableRequest request);
//
//    List<TweetImageProjection> getUserTweetImages(TweetPageableRequest request);
//
//    List<TweetsUserProjection> getRepliesByUserId(Long userId);
//
//    List<TweetProjection> getNotificationsFromTweetAuthors(Long userId);
//
//    List<TweetProjection> getTweetsByIds(List<Long> tweetIds);
//
//    Page<TweetProjection> getTweetsByUserIds(TweetUserIdsRequest request, Pageable pageable);

    // NEW
    NotificationTweetResponse getNotificationTweet(Long tweetId);

    Boolean isTweetExists(Long tweetId);

    ChatTweetResponse getChatTweet(Long tweetId);
}
