package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.NotificationInfoResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.MENTIONS;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_ID;
import static com.gmail.javacoded78.constants.PathConstants.SUBSCRIBES;
import static com.gmail.javacoded78.constants.PathConstants.TIMELINE;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_NOTIFICATION;
import static com.gmail.javacoded78.constants.PathConstants.USER;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_NOTIFICATION)
public class NotificationController {

    private final NotificationMapper notificationMapper;

    @GetMapping(USER)
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<NotificationResponse> response = notificationMapper.getUserNotifications(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(MENTIONS)
    public ResponseEntity<List<TweetResponse>> getUserMentionsNotifications(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = notificationMapper.getUserMentionsNotifications(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(SUBSCRIBES)
    public ResponseEntity<List<NotificationUserResponse>> getTweetAuthorsNotifications() {
        return ResponseEntity.ok(notificationMapper.getTweetAuthorsNotifications());
    }

    @GetMapping(NOTIFICATION_ID)
    public ResponseEntity<NotificationInfoResponse> getUserNotificationById(@PathVariable("notificationId") Long notificationId) {
        return ResponseEntity.ok(notificationMapper.getUserNotificationById(notificationId));
    }

    @GetMapping(TIMELINE)
    public ResponseEntity<List<TweetResponse>> getNotificationsFromTweetAuthors(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = notificationMapper.getNotificationsFromTweetAuthors(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }
}
