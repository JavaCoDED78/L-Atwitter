package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.gmail.javacoded78.constants.FeignConstants.NOTIFICATION_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_NOTIFICATION;
import static com.gmail.javacoded78.constants.PathConstants.TWEET;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_TWEET_ID;

@CircuitBreaker(name = NOTIFICATION_SERVICE)
@FeignClient(name = NOTIFICATION_SERVICE, path = API_V1_NOTIFICATION, configuration = FeignConfiguration.class)
public interface NotificationClient {

    @PostMapping(TWEET)
    NotificationResponse sendTweetNotification(@RequestBody NotificationRequest request);

    @GetMapping(TWEET_TWEET_ID)
    void sendTweetNotificationToSubscribers(@PathVariable("tweetId") Long tweetId);
}
