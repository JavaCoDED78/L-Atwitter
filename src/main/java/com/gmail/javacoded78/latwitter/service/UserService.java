package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;

import java.util.List;

public interface UserService {

    User getUserById(Long userId);

    List<Tweet> getTweets();

    Tweet getTweetById(Long tweetId);

    List<Tweet> createTweet(Tweet tweet);

    List<Tweet> deleteTweet(Long tweetId);
}
