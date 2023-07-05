package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;

import java.util.List;

public interface TweetService {
    List<Tweet> getTweets();

    Tweet getTweetById(Long tweetId);

    List<Tweet> getTweetsByUser(User user);

    List<Tweet> createTweet(Tweet tweet);

    List<Tweet> deleteTweet(Long tweetId);

    Tweet likeTweet(Long tweetId);

    Tweet retweet(Long tweetId);
}
