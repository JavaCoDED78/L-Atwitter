package com.gmail.javacoded78.service;

import com.gmail.javacoded78.models.Tweet;
import com.gmail.javacoded78.projection.TweetImageProjection;
import com.gmail.javacoded78.projection.TweetProjection;
import com.gmail.javacoded78.projection.TweetsProjection;
import com.gmail.javacoded78.projection.TweetsUserProjection;
import com.gmail.javacoded78.tweet.TweetPageableRequest;
import org.springframework.data.domain.Page;

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

    List<TweetsProjection> getNotificationsFromTweetAuthors(Long userId);
}
