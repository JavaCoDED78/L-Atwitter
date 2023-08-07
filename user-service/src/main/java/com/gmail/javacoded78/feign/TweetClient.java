package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.gmail.javacoded78.constants.FeignConstants.TWEET_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.ID_TWEET_ID;

@CircuitBreaker(name = TWEET_SERVICE)
@FeignClient(value = TWEET_SERVICE, path = API_V1_TWEETS, configuration = FeignConfiguration.class)
public interface TweetClient {

    @GetMapping(ID_TWEET_ID)
    Boolean isTweetExists(@PathVariable("tweetId") Long tweetId);
}
