package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.javacoded78.constants.FeignConstants.TWEET_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_TWEETS;

@CircuitBreaker(name = TWEET_SERVICE, fallbackMethod = "defaultEmptyTweetList")
@FeignClient(value = TWEET_SERVICE, configuration = FeignConfiguration.class)
public interface TweetClient {

    @PostMapping(API_V1_TWEETS + "/tag/ids")
    List<TweetResponse> getTweetsByIds(@RequestBody IdsRequest request);

    default ArrayList<TweetResponse> defaultEmptyTweetList(Throwable throwable) {
        return new ArrayList<>();
    }
}
