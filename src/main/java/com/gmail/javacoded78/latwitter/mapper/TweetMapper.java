package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.request.TweetRequest;
import com.gmail.javacoded78.latwitter.dto.response.NotificationResponse;
import com.gmail.javacoded78.latwitter.dto.response.TweetResponse;
import com.gmail.javacoded78.latwitter.model.ReplyType;
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

    List<TweetResponse> convertListToResponse(List<Tweet> tweets) {
        return tweets.stream()
                .map(this::convertToTweetResponse)
                .collect(Collectors.toList());
    }

    public List<TweetResponse> getTweets() {
        return convertListToResponse(tweetService.getTweets());
    }

    public List<TweetResponse> getMediaTweets() {
        return convertListToResponse(tweetService.getMediaTweets());
    }

    public TweetResponse getTweetById(Long tweetId) {
        return convertToTweetResponse(tweetService.getTweetById(tweetId));
    }

    public TweetResponse createTweet(TweetRequest tweetRequest) {
        return convertToTweetResponse(tweetService.createTweet(convertToTweetEntity(tweetRequest)));
    }

    public TweetResponse createPoll(TweetRequest tweetRequest) {
        return convertToTweetResponse(tweetService.createPoll(tweetRequest.getPollDateTime(), tweetRequest.getChoices(),
                convertToTweetEntity(tweetRequest)));
    }

    public TweetResponse deleteTweet(Long tweetId) {
        TweetResponse tweetResponse = convertToTweetResponse(tweetService.deleteTweet(tweetId));
        tweetResponse.setTweetDeleted(true);
        return tweetResponse;
    }

    public NotificationResponse likeTweet(Long tweetId) {
        return userMapper.convertToNotificationResponse(tweetService.likeTweet(tweetId));
    }

    public NotificationResponse retweet(Long tweetId) {
        return userMapper.convertToNotificationResponse(tweetService.retweet(tweetId));
    }

    public List<TweetResponse> searchTweets(String text) {
        return convertListToResponse(tweetService.searchTweets(text));
    }

    public TweetResponse replyTweet(Long tweetId, TweetRequest tweetRequest) {
        return convertToTweetResponse(tweetService.replyTweet(tweetId, convertToTweetEntity(tweetRequest)));
    }

    public TweetResponse quoteTweet(Long tweetId, TweetRequest tweetRequest) {
        return convertToTweetResponse(tweetService.quoteTweet(tweetId, convertToTweetEntity(tweetRequest)));
    }

    public TweetResponse changeTweetReplyType(Long tweetId, ReplyType replyType) {
        return convertToTweetResponse(tweetService.changeTweetReplyType(tweetId, replyType));
    }

    public TweetResponse voteInPoll(Long tweetId, Long pollChoiceId) {
        return convertToTweetResponse(tweetService.voteInPoll(tweetId, pollChoiceId));
    }
}
