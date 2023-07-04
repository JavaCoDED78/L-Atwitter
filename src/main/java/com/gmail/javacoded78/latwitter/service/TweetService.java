package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Tweet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TweetService {

    List<Tweet> getTweets();

    Tweet getTweetById(Long tweetId);

    List<Tweet> createTweet(Tweet tweet, MultipartFile multipartFile);

    List<Tweet> deleteTweet(Long tweetId);
}
