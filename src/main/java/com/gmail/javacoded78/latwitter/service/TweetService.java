package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Notification;
import com.gmail.javacoded78.latwitter.model.ReplyType;
import com.gmail.javacoded78.latwitter.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TweetService {
    Page<Tweet> getTweets(Pageable pageable);

    Tweet getTweetById(Long tweetId);

    Page<Tweet> getMediaTweets(Pageable pageable);

    Page<Tweet> getTweetsWithVideo(Pageable pageable);

    Tweet createTweet(Tweet tweet);

    Tweet createPoll(Long pollDateTime, List<String> choices, Tweet tweet);

    Tweet deleteTweet(Long tweetId);

    List<Tweet> searchTweets(String text);

    Notification likeTweet(Long tweetId);

    Notification retweet(Long tweetId);

    Tweet replyTweet(Long tweetId, Tweet reply);

    Tweet quoteTweet(Long tweetId, Tweet quote);

    Tweet changeTweetReplyType(Long tweetId, ReplyType replyType);

    Tweet voteInPoll(Long tweetId, Long pollChoiceId);
}
