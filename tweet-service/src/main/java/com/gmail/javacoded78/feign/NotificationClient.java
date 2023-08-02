package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.gmail.javacoded78.constants.FeignConstants.NOTIFICATION_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_NOTIFICATION;

@FeignClient(name = NOTIFICATION_SERVICE, configuration = FeignConfiguration.class)
public interface NotificationClient {

    @PostMapping(API_V1_NOTIFICATION + "/tweet")
    NotificationResponse sendTweetNotification(@RequestBody NotificationRequest request);

    @GetMapping(API_V1_NOTIFICATION + "/tweet/{tweetId}")
    void sendTweetNotificationToSubscribers(@PathVariable("tweetId") Long tweetId);
}
