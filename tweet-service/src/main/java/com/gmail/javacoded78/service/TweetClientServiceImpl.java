package com.gmail.javacoded78.service;

import com.gmail.javacoded78.models.Tweet;
import com.gmail.javacoded78.projection.TweetImageProjection;
import com.gmail.javacoded78.projection.TweetProjection;
import com.gmail.javacoded78.projection.TweetsProjection;
import com.gmail.javacoded78.projection.TweetsUserProjection;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.tweet.TweetPageableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<TweetsProjection> getNotificationsFromTweetAuthors(Long userId) {
        return tweetRepository.getNotificationsFromTweetAuthors(userId);
    }
}