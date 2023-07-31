package com.gmail.javacoded78.service;

import com.gmail.javacoded78.client.tweet.TweetUserIdsRequest;
import com.gmail.javacoded78.client.tweet.TweetPageableRequest;
import com.gmail.javacoded78.common.models.Tweet;
import com.gmail.javacoded78.common.projection.TweetImageProjection;
import com.gmail.javacoded78.common.projection.TweetProjection;
import com.gmail.javacoded78.common.projection.TweetsUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TweetClientService {

    Optional<Tweet> getTweetById(Long userId);

    List<TweetsUserProjection> getTweetsByUserId(Long userId);

    Optional<TweetsUserProjection> getPinnedTweetByUserId(Long userId);

    Page<TweetProjection> getAllUserMediaTweets(TweetPageableRequest request);

    Page<TweetProjection> getUserMentions(TweetPageableRequest request);

    List<TweetImageProjection> getUserTweetImages(TweetPageableRequest request);

    List<TweetsUserProjection> getRepliesByUserId(Long userId);

    List<TweetProjection> getNotificationsFromTweetAuthors(Long userId);

    List<TweetProjection> getTweetsByIds(List<Long> tweetIds);

    Page<TweetProjection> getTweetsByUserIds(TweetUserIdsRequest request, Pageable pageable);
}
