package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Notification;
import com.gmail.javacoded78.latwitter.model.ReplyType;
import com.gmail.javacoded78.latwitter.model.Tweet;

import java.util.List;

public interface TweetService {
    List<Tweet> getTweets();

    Tweet getTweetById(Long tweetId);

    List<Tweet> getMediaTweets();

    Tweet createTweet(Tweet tweet);

    Tweet createPoll(Long pollDateTime, List<String> choices, Tweet tweet);

    String deleteTweet(Long tweetId);

    List<Tweet> searchTweets(String text);

    Notification likeTweet(Long tweetId);

    Notification retweet(Long tweetId);

    Tweet replyTweet(Long tweetId, Tweet reply);

    Tweet changeTweetReplyType(Long tweetId, ReplyType replyType);

    Tweet voteInPoll(Long tweetId, Long pollChoiceId);
}
