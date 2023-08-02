package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.request.TweetDeleteRequest;
import com.gmail.javacoded78.dto.request.TweetRequest;
import com.gmail.javacoded78.dto.response.NotificationReplyResponse;
import com.gmail.javacoded78.dto.response.ProfileTweetImageResponse;
import com.gmail.javacoded78.dto.response.TweetAdditionalInfoResponse;
import com.gmail.javacoded78.dto.response.TweetImageResponse;
import com.gmail.javacoded78.dto.response.TweetUserResponse;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.mapper.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TWEETS;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_FEED;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_FEED_ADD;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_TWEET;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_USER_ADD_TWEET;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_USER_UPDATE_TWEET;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_TWEETS)
public class TweetController {

    private final TweetMapper tweetMapper;
    private final WebSocketClient webSocketClient;

    @GetMapping
    public ResponseEntity<List<TweetResponse>> getTweets(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getTweets(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetResponse> getTweetById(@PathVariable("tweetId") Long tweetId) {
        return ResponseEntity.ok(tweetMapper.getTweetById(tweetId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TweetUserResponse>> getUserTweets(@PathVariable Long userId,
                                                                 @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetUserResponse> response = tweetMapper.getUserTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/media/user/{userId}")
    public ResponseEntity<List<TweetResponse>> getUserMediaTweets(@PathVariable Long userId,
                                                                  @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getUserMediaTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/user/mentions")
    public ResponseEntity<List<TweetResponse>> getUserMentions(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getUserMentions(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/images/{userId}")
    public ResponseEntity<List<ProfileTweetImageResponse>> getUserTweetImages(@PathVariable Long userId) {
        return ResponseEntity.ok(tweetMapper.getUserTweetImages(userId));
    }

    @GetMapping("/{tweetId}/info")
    public ResponseEntity<TweetAdditionalInfoResponse> getTweetAdditionalInfoById(@PathVariable("tweetId") Long tweetId) {
        return ResponseEntity.ok(tweetMapper.getTweetAdditionalInfoById(tweetId));
    }

    @GetMapping("/{tweetId}/replies") // TODO add pagination
    public ResponseEntity<List<TweetResponse>> getRepliesByTweetId(@PathVariable("tweetId") Long tweetId) {
        return ResponseEntity.ok(tweetMapper.getRepliesByTweetId(tweetId));
    }

    @GetMapping("/{tweetId}/quotes")
    public ResponseEntity<List<TweetResponse>> getQuotesByTweetId(@PathVariable("tweetId") Long tweetId,
                                                                  @PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getQuotesByTweetId(pageable, tweetId);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/media")
    public ResponseEntity<List<TweetResponse>> getMediaTweets(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getMediaTweets(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/video")
    public ResponseEntity<List<TweetResponse>> getTweetsWithVideo(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getTweetsWithVideo(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/follower")
    public ResponseEntity<List<TweetResponse>> getFollowersTweets(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getFollowersTweets(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<TweetResponse>> getScheduledTweets(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getScheduledTweets(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping("/upload") // TODO add endpoint to frontend
    public ResponseEntity<TweetImageResponse> uploadTweetImage(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(tweetMapper.uploadTweetImage(file));
    }

    @PostMapping
    public ResponseEntity<TweetResponse> createTweet(@RequestBody TweetRequest tweetRequest) {
        TweetResponse tweet = tweetMapper.createTweet(tweetRequest);
        webSocketClient.send(TOPIC_FEED_ADD, tweet);
        webSocketClient.send(TOPIC_USER_ADD_TWEET + tweet.getUser().getId(), tweet);
        return ResponseEntity.ok(tweet);
    }

    @PostMapping("/schedule")
    public ResponseEntity<TweetResponse> createScheduledTweet(@RequestBody TweetRequest tweetRequest) {
        return ResponseEntity.ok(tweetMapper.createTweet(tweetRequest));
    }

    @PutMapping("/schedule")
    public ResponseEntity<TweetResponse> updateScheduledTweet(@RequestBody TweetRequest tweetRequest) {
        return ResponseEntity.ok(tweetMapper.updateScheduledTweet(tweetRequest));
    }

    @DeleteMapping("/schedule")
    public ResponseEntity<String> deleteScheduledTweets(@RequestBody TweetDeleteRequest tweetRequest) {
        return ResponseEntity.ok(tweetMapper.deleteScheduledTweets(tweetRequest));
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<String> deleteTweet(@PathVariable("tweetId") Long tweetId) {
        return ResponseEntity.ok(tweetMapper.deleteTweet(tweetId));
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<List<TweetResponse>> searchTweets(@PathVariable("text") String text,
                                                            @PageableDefault Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.searchTweets(text, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping("/reply/{userId}/{tweetId}")
    public ResponseEntity<NotificationReplyResponse> replyTweet(@PathVariable("userId") Long userId,
                                                                @PathVariable("tweetId") Long tweetId,
                                                                @RequestBody TweetRequest tweetRequest) {
        NotificationReplyResponse notification = tweetMapper.replyTweet(tweetId, tweetRequest);
        webSocketClient.send(TOPIC_FEED, notification);
        webSocketClient.send(TOPIC_TWEET + notification.getTweetId(), notification);
        webSocketClient.send(TOPIC_USER_UPDATE_TWEET + userId, notification);
        return ResponseEntity.ok(notification);
    }

    @PostMapping("/quote/{userId}/{tweetId}")
    public ResponseEntity<TweetResponse> quoteTweet(@PathVariable("userId") Long userId,
                                                    @PathVariable("tweetId") Long tweetId,
                                                    @RequestBody TweetRequest tweetRequest) {
        TweetResponse tweet = tweetMapper.quoteTweet(tweetId, tweetRequest);
        webSocketClient.send(TOPIC_FEED_ADD, tweet);
        webSocketClient.send(TOPIC_TWEET + tweet.getId(), tweet);
        webSocketClient.send(TOPIC_USER_ADD_TWEET + userId, tweet);
        return ResponseEntity.ok(tweet);
    }

    @GetMapping("/reply/change/{userId}/{tweetId}")
    public ResponseEntity<TweetResponse> changeTweetReplyType(@PathVariable("userId") Long userId,
                                                              @PathVariable("tweetId") Long tweetId,
                                                              @RequestParam ReplyType replyType) {
        TweetResponse tweet = tweetMapper.changeTweetReplyType(tweetId, replyType);
        webSocketClient.send(TOPIC_FEED, tweet);
        webSocketClient.send(TOPIC_TWEET + tweet.getId(), tweet);
        webSocketClient.send(TOPIC_USER_UPDATE_TWEET + userId, tweet);
        return ResponseEntity.ok(tweet);
    }
}
