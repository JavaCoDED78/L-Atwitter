package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationTweetResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.gmail.javacoded78.constants.FeignConstants.TWEET_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.IDS;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID;

@CircuitBreaker(name = TWEET_SERVICE)
@FeignClient(name = TWEET_SERVICE, path = API_V1_TWEETS, configuration = FeignConfiguration.class)
public interface TweetClient {

    @GetMapping(TWEET_ID)
    TweetResponse getTweetById(@PathVariable("tweetId") Long tweetId);

    @GetMapping(NOTIFICATION_TWEET_ID)
    NotificationTweetResponse getNotificationTweet(@PathVariable("tweetId") Long tweetId);

    @PostMapping(IDS)
    List<TweetResponse> getTweetsByIds(@RequestBody IdsRequest idsRequest);
}
