package com.gmail.javacoded78.service;

import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.projection.TweetProjection;

import java.util.List;

public interface PollService {

    TweetProjection createPoll(Long pollDateTime, List<String> choices, Tweet tweet);

    TweetProjection voteInPoll(Long tweetId, Long pollId, Long pollChoiceId);
}