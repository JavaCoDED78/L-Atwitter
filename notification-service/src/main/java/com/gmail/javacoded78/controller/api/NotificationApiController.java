package com.gmail.javacoded78.controller.api;

import com.gmail.javacoded78.dto.NotificationRequest;
import com.gmail.javacoded78.dto.notification.NotificationResponse;
import com.gmail.javacoded78.mapper.NotificationClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.javacoded78.controller.PathConstants.API_V1_NOTIFICATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_NOTIFICATION)
public class NotificationApiController {

    private final NotificationClientMapper notificationClientMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/list")
    public void sendListNotification(@RequestBody NotificationRequest request) {
        NotificationResponse notification = notificationClientMapper.sendListNotification(request);

        if (notification.getId() != null) {
            messagingTemplate.convertAndSend("/topic/notifications/" + notification.getUser().getId(), notification);
        }
    }

    @PostMapping("/user")
    public void sendUserNotification(@RequestBody NotificationRequest request) {
        NotificationResponse notification = notificationClientMapper.sendUserNotification(request);

        if (notification.getId() != null) {
            messagingTemplate.convertAndSend("/topic/notifications/" + notification.getUser().getId(), notification);
        }
    }

    @PostMapping("/tweet")
    public NotificationResponse sendTweetNotification(@RequestBody NotificationRequest request) {
        NotificationResponse notification = notificationClientMapper.sendTweetNotification(request);

        if (notification.getId() != null) {
            messagingTemplate.convertAndSend("/topic/notifications/" + notification.getTweet().getAuthorId(), notification);
        }
        messagingTemplate.convertAndSend("/topic/feed", notification);
        messagingTemplate.convertAndSend("/topic/tweet/" + notification.getTweet().getId(), notification);
        return notification;
    }

    @GetMapping("/tweet/{tweetId}")
    public void sendTweetNotificationToSubscribers(@PathVariable("tweetId") Long tweetId) {
        notificationClientMapper.sendTweetNotificationToSubscribers(tweetId);
    }
}
