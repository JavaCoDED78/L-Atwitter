package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.client.tweet.TweetUserIdsRequest;
import com.gmail.javacoded78.common.models.Tweet;
import com.gmail.javacoded78.common.projection.TweetImageProjection;
import com.gmail.javacoded78.common.projection.TweetProjection;
import com.gmail.javacoded78.common.projection.TweetsProjection;
import com.gmail.javacoded78.common.projection.TweetsUserProjection;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.client.tweet.TweetPageableRequest;
import com.gmail.javacoded78.service.TweetClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetClientServiceImpl implements TweetClientService {

    private final TweetRepository tweetRepository;

    @Override
    public Optional<Tweet> getTweetById(Long userId) {
        return tweetRepository.findById(userId);
    }

    @Override
    public List<TweetsUserProjection> getTweetsByUserId(Long userId) {
        return tweetRepository.getTweetsByUserId(userId);
    }

    @Override
    public Optional<TweetsUserProjection> getPinnedTweetByUserId(Long userId) {
        return tweetRepository.getPinnedTweetByUserId(userId);
    }

    @Override
    public Page<TweetProjection> getAllUserMediaTweets(TweetPageableRequest request) {
        return tweetRepository.getAllUserMediaTweets(request.getUserId(), request.getPageable());
    }

    @Override
    public Page<TweetProjection> getUserMentions(TweetPageableRequest request) {
        return tweetRepository.getUserMentions(request.getUserId(), request.getPageable());
    }

    @Override
    public List<TweetImageProjection> getUserTweetImages(TweetPageableRequest request) {
        return tweetRepository.getUserTweetImages(request.getUserId(), request.getPageable());
    }

    @Override
    public List<TweetsUserProjection> getRepliesByUserId(Long userId) {
        return tweetRepository.getRepliesByUserId(userId);
    }

    @Override
    public List<TweetProjection> getNotificationsFromTweetAuthors(Long userId) {
        return tweetRepository.getNotificationsFromTweetAuthors(userId).stream()
                .map(TweetsProjection::getTweet)
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetProjection> getTweetsByTagName(String tagName) {
        return tweetRepository.getTweetsByTagName(tagName).stream()
                .map(TweetsProjection::getTweet)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TweetProjection> getTweetsByUserIds(TweetUserIdsRequest request) {
        return tweetRepository.findTweetsByUserIds(request.getUserIds(), request.getPageable());
    }
}