package com.gmail.javacoded78.service;

import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduledTweetService {

    Page<TweetProjection> getScheduledTweets(Pageable pageable);

    TweetProjection createScheduledTweet(Tweet tweet);

    TweetProjection updateScheduledTweet(Tweet tweet);

    String deleteScheduledTweets(List<Long> tweetsIds);
}
