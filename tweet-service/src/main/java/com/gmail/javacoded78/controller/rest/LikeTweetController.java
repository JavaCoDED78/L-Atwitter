package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.TweetResponse;
import com.gmail.javacoded78.dto.UserResponse;
import com.gmail.javacoded78.dto.notification.NotificationResponse;
import com.gmail.javacoded78.dto.notification.NotificationTweetResponse;
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

import static com.gmail.javacoded78.controller.PathConstants.UI_V1_TWEETS;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_TWEETS)
public class LikeTweetController {

    private final LikeTweetMapper likeTweetMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/liked/user/{userId}") // TODO change endpoint in frontend
    public ResponseEntity<List<TweetResponse>> getUserLikedTweets(@PathVariable Long userId,
                                                                  @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = likeTweetMapper.getUserLikedTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/{tweetId}/liked-users")
    public ResponseEntity<List<UserResponse>> getLikedUsersByTweetId(@PathVariable("tweetId") Long tweetId,
                                                                     @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = likeTweetMapper.getLikedUsersByTweetId(tweetId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/like/{userId}/{tweetId}")
    public ResponseEntity<NotificationTweetResponse> likeTweet(@PathVariable("userId") Long userId,
                                                               @PathVariable("tweetId") Long tweetId) {
        NotificationResponse notification = likeTweetMapper.likeTweet(tweetId);
        messagingTemplate.convertAndSend("/topic/user/update/tweet/" + userId, notification);
        return ResponseEntity.ok(notification.getTweet());
    }
}
