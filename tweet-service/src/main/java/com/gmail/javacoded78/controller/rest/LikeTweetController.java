package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationTweetResponse;
import com.gmail.javacoded78.feign.WebSocketClient;
import com.gmail.javacoded78.mapper.LikeTweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.LIKED_USER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.LIKE_USER_ID_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID_LIKED_USERS;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TWEETS;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_USER_UPDATE_TWEET;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_TWEETS)
public class LikeTweetController {

    private final LikeTweetMapper likeTweetMapper;
    private final WebSocketClient webSocketClient;

    @GetMapping(LIKED_USER_USER_ID)
    public ResponseEntity<List<TweetResponse>> getUserLikedTweets(@PathVariable Long userId,
                                                                  @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = likeTweetMapper.getUserLikedTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(TWEET_ID_LIKED_USERS)
    public ResponseEntity<List<UserResponse>> getLikedUsersByTweetId(@PathVariable("tweetId") Long tweetId,
                                                                     @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = likeTweetMapper.getLikedUsersByTweetId(tweetId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(LIKE_USER_ID_TWEET_ID)
    public ResponseEntity<NotificationTweetResponse> likeTweet(@PathVariable("userId") Long userId,
                                                               @PathVariable("tweetId") Long tweetId) {
        NotificationResponse notification = likeTweetMapper.likeTweet(tweetId);
        webSocketClient.send(TOPIC_USER_UPDATE_TWEET + userId, notification);
        return ResponseEntity.ok(notification.getTweet());
    }
}
