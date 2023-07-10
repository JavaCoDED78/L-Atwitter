package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Tweet;

import java.util.List;

public interface TweetService {
    List<Tweet> getTweets();

    Tweet getTweetById(Long tweetId);

    List<Tweet> getMediaTweets();

    Tweet createTweet(Tweet tweet);

    List<Tweet> searchTweets(String text);

    Tweet likeTweet(Long tweetId);

    Tweet retweet(Long tweetId);

    Tweet replyTweet(Long tweetId, Tweet reply);
}
