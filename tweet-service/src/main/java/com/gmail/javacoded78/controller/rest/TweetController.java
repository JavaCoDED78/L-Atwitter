package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.request.TweetRequest;
import com.gmail.javacoded78.dto.response.NotificationReplyResponse;
import com.gmail.javacoded78.dto.response.ProfileTweetImageResponse;
import com.gmail.javacoded78.dto.response.TweetAdditionalInfoResponse;
import com.gmail.javacoded78.dto.response.TweetImageResponse;
import com.gmail.javacoded78.dto.response.TweetUserResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.feign.WebSocketClient;
import com.gmail.javacoded78.mapper.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.FOLLOWER;
import static com.gmail.javacoded78.constants.PathConstants.IMAGES_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.IMAGE_TAGGED;
import static com.gmail.javacoded78.constants.PathConstants.MEDIA;
import static com.gmail.javacoded78.constants.PathConstants.MEDIA_USER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.QUOTE_USER_ID_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.REPLY_CHANGE_USER_ID_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.REPLY_USER_ID_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_TEXT;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID_INFO;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID_QUOTES;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID_REPLIES;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.UPLOAD;
import static com.gmail.javacoded78.constants.PathConstants.USER_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.VIDEO;
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
    public ResponseEntity<List<TweetResponse>> getTweets(@PageableDefault Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getTweets(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(TWEET_ID)
    public ResponseEntity<TweetResponse> getTweetById(@PathVariable("tweetId") Long tweetId) {
        return ResponseEntity.ok(tweetMapper.getTweetById(tweetId));
    }

    @GetMapping(USER_USER_ID)
    public ResponseEntity<List<TweetUserResponse>> getUserTweets(@PathVariable("userId") Long userId,
                                                                 @PageableDefault Pageable pageable) {
        HeaderResponse<TweetUserResponse> response = tweetMapper.getUserTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(MEDIA_USER_USER_ID)
    public ResponseEntity<List<TweetResponse>> getUserMediaTweets(@PathVariable("userId") Long userId,
                                                                  @PageableDefault Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getUserMediaTweets(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(IMAGES_USER_ID)
    public ResponseEntity<List<ProfileTweetImageResponse>> getUserTweetImages(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(tweetMapper.getUserTweetImages(userId));
    }

    @GetMapping(TWEET_ID_INFO)
    public ResponseEntity<TweetAdditionalInfoResponse> getTweetAdditionalInfoById(@PathVariable("tweetId") Long tweetId) {
        return ResponseEntity.ok(tweetMapper.getTweetAdditionalInfoById(tweetId));
    }

    @GetMapping(TWEET_ID_REPLIES)
    public ResponseEntity<List<TweetResponse>> getRepliesByTweetId(@PathVariable("tweetId") Long tweetId) {
        return ResponseEntity.ok(tweetMapper.getRepliesByTweetId(tweetId));
    }

    @GetMapping(TWEET_ID_QUOTES)
    public ResponseEntity<List<TweetResponse>> getQuotesByTweetId(@PathVariable("tweetId") Long tweetId,
                                                                  @PageableDefault Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getQuotesByTweetId(pageable, tweetId);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(MEDIA)
    public ResponseEntity<List<TweetResponse>> getMediaTweets(@PageableDefault Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getMediaTweets(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(VIDEO)
    public ResponseEntity<List<TweetResponse>> getTweetsWithVideo(@PageableDefault Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getTweetsWithVideo(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(FOLLOWER)
    public ResponseEntity<List<TweetResponse>> getFollowersTweets(@PageableDefault Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.getFollowersTweets(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping(UPLOAD)
    public ResponseEntity<TweetImageResponse> uploadTweetImage(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(tweetMapper.uploadTweetImage(file));
    }

    @GetMapping(IMAGE_TAGGED)
    public ResponseEntity<List<UserResponse>> getTaggedImageUsers(@PathVariable("tweetId") Long tweetId,
                                                                  @PageableDefault Pageable pageable) {
        HeaderResponse<UserResponse> response = tweetMapper.getTaggedImageUsers(tweetId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping
    public ResponseEntity<TweetResponse> createTweet(@RequestBody TweetRequest tweetRequest) {
        TweetResponse tweet = tweetMapper.createTweet(tweetRequest);
        webSocketClient.send(TOPIC_FEED_ADD, tweet);
        webSocketClient.send(TOPIC_USER_ADD_TWEET + tweet.getUser().getId(), tweet);
        return ResponseEntity.ok(tweet);
    }

    @DeleteMapping(TWEET_ID)
    public ResponseEntity<String> deleteTweet(@PathVariable("tweetId") Long tweetId) {
        return ResponseEntity.ok(tweetMapper.deleteTweet(tweetId));
    }

    @GetMapping(SEARCH_TEXT)
    public ResponseEntity<List<TweetResponse>> searchTweets(@PathVariable("text") String text,
                                                            @PageableDefault Pageable pageable) {
        HeaderResponse<TweetResponse> response = tweetMapper.searchTweets(text, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping(REPLY_USER_ID_TWEET_ID)
    public ResponseEntity<NotificationReplyResponse> replyTweet(@PathVariable("userId") Long userId,
                                                                @PathVariable("tweetId") Long tweetId,
                                                                @RequestBody TweetRequest tweetRequest) {
        NotificationReplyResponse notification = tweetMapper.replyTweet(tweetId, tweetRequest);
        webSocketClient.send(TOPIC_FEED, notification);
        webSocketClient.send(TOPIC_TWEET + notification.getTweetId(), notification);
        webSocketClient.send(TOPIC_USER_UPDATE_TWEET + userId, notification);
        return ResponseEntity.ok(notification);
    }

    @PostMapping(QUOTE_USER_ID_TWEET_ID)
    public ResponseEntity<TweetResponse> quoteTweet(@PathVariable("userId") Long userId,
                                                    @PathVariable("tweetId") Long tweetId,
                                                    @RequestBody TweetRequest tweetRequest) {
        TweetResponse tweet = tweetMapper.quoteTweet(tweetId, tweetRequest);
        webSocketClient.send(TOPIC_FEED_ADD, tweet);
        webSocketClient.send(TOPIC_TWEET + tweet.getId(), tweet);
        webSocketClient.send(TOPIC_USER_ADD_TWEET + userId, tweet);
        return ResponseEntity.ok(tweet);
    }

    @GetMapping(REPLY_CHANGE_USER_ID_TWEET_ID)
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
