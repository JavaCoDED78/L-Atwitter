package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.request.TweetRequest;
import com.gmail.javacoded78.latwitter.dto.response.NotificationResponse;
import com.gmail.javacoded78.latwitter.dto.response.TweetResponse;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TweetMapper {

    private final ModelMapper modelMapper;
    private final TweetService tweetService;
    @Lazy
    private final UserMapper userMapper;

    private Tweet convertToTweetEntity(TweetRequest tweetRequest) {
        return modelMapper.map(tweetRequest, Tweet.class);
    }

    private TweetResponse convertToTweetResponse(Tweet tweet) {
        return modelMapper.map(tweet, TweetResponse.class);
    }

    List<TweetResponse> convertListToResponseDto(List<Tweet> tweets) {
        return tweets.stream()
                .map(this::convertToTweetResponse)
                .collect(Collectors.toList());
    }

    public List<TweetResponse> getTweets() {
        return convertListToResponseDto(tweetService.getTweets());
    }

    public List<TweetResponse> getMediaTweets() {
        return convertListToResponseDto(tweetService.getMediaTweets());
    }

    public TweetResponse getTweetById(Long tweetId) {
        return convertToTweetResponse(tweetService.getTweetById(tweetId));
    }

    public TweetResponse createTweet(TweetRequest tweetRequest) {
        return convertToTweetResponse(tweetService.createTweet(convertToTweetEntity(tweetRequest)));
    }

    public String deleteTweet(Long tweetId) {
        return tweetService.deleteTweet(tweetId);
    }

    public NotificationResponse likeTweet(Long tweetId) {
        return userMapper.convertToNotificationResponse(tweetService.likeTweet(tweetId));
    }

    public NotificationResponse retweet(Long tweetId) {
        return userMapper.convertToNotificationResponse(tweetService.retweet(tweetId));
    }

    public List<TweetResponse> searchTweets(String text) {
        return convertListToResponseDto(tweetService.searchTweets(text));
    }

    public TweetResponse replyTweet(Long tweetId, TweetRequest tweetRequest) {
        return convertToTweetResponse(tweetService.replyTweet(tweetId, convertToTweetEntity(tweetRequest)));
    }
}
