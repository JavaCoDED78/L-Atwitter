package com.gmail.javacoded78.controller.api;

import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.feign.WebSocketClient;
import com.gmail.javacoded78.mapper.NotificationClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_NOTIFICATION;
import static com.gmail.javacoded78.constants.PathConstants.LIST;
import static com.gmail.javacoded78.constants.PathConstants.TWEET;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.USER;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_FEED;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_NOTIFICATIONS;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_TWEET;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_NOTIFICATION)
public class NotificationApiController {

    private final NotificationClientMapper notificationClientMapper;
    private final WebSocketClient webSocketClient;

    @PostMapping(LIST)
    public void sendListNotification(@RequestBody NotificationRequest request) {
        NotificationResponse notification = notificationClientMapper.sendNotification(request);

        if (notification.getId() != null) {
            webSocketClient.send(TOPIC_NOTIFICATIONS + notification.getUser().getId(), notification);
        }
    }

    @PostMapping(USER)
    public void sendUserNotification(@RequestBody NotificationRequest request) {
        NotificationResponse notification = notificationClientMapper.sendNotification(request);

        if (notification.getId() != null) {
            webSocketClient.send(TOPIC_NOTIFICATIONS + notification.getUser().getId(), notification);
        }
    }

    @PostMapping(TWEET)
    public NotificationResponse sendTweetNotification(@RequestBody NotificationRequest request) {
        NotificationResponse notification = notificationClientMapper.sendNotification(request);

        if (notification.getId() != null) {
            webSocketClient.send(TOPIC_NOTIFICATIONS + notification.getTweet().getAuthorId(), notification);
        }
        webSocketClient.send(TOPIC_FEED, notification);
        webSocketClient.send(TOPIC_TWEET + notification.getTweet().getId(), notification);
        return notification;
    }

    @GetMapping(TWEET_TWEET_ID)
    public void sendTweetNotificationToSubscribers(@PathVariable("tweetId") Long tweetId) {
        notificationClientMapper.sendTweetNotificationToSubscribers(tweetId);
    }
}
