package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.ChatTweetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.gmail.javacoded78.controller.PathConstants.API_V1_TWEETS;

@FeignClient(name = "tweet-service", configuration = FeignConfiguration.class)
public interface TweetClient {

    @GetMapping(API_V1_TWEETS + "/id/{tweetId}")
    Boolean isTweetExists(@PathVariable("tweetId") Long tweetId);

    @GetMapping(API_V1_TWEETS + "/chat/{tweetId}")
    ChatTweetResponse getChatTweet(@PathVariable("tweetId") Long tweetId);
}
