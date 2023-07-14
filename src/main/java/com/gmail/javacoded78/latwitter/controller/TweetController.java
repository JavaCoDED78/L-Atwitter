package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.request.TweetRequest;
import com.gmail.javacoded78.latwitter.dto.request.VoteRequest;
import com.gmail.javacoded78.latwitter.dto.response.TweetHeaderResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationTweetResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.mapper.TweetMapper;
import com.gmail.javacoded78.latwitter.model.ReplyType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tweets")
public class TweetController {

    private final TweetMapper tweetMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public ResponseEntity<List<TweetResponse>> getTweets(@PageableDefault(size = 10) Pageable pageable) {
        TweetHeaderResponse response = tweetMapper.getTweets(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getTweets());
    }

    @GetMapping("/{tweetId}")
    public ResponseEntity<TweetResponse> getTweetById(@PathVariable Long tweetId) {
        return ResponseEntity.ok(tweetMapper.getTweetById(tweetId));
    }

    @GetMapping("/media")
    public ResponseEntity<List<TweetResponse>> getMediaTweetsPageable(@PageableDefault(size = 10) Pageable pageable) {
        TweetHeaderResponse response = tweetMapper.getMediaTweets(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getTweets());
    }

    @GetMapping("/video")
    public ResponseEntity<List<TweetResponse>> getTweetsWithVideo(@PageableDefault(size = 10) Pageable pageable) {
        TweetHeaderResponse response = tweetMapper.getTweetsWithVideo(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getTweets());
    }

    @PostMapping
    public ResponseEntity<TweetResponse> createTweet(@RequestBody TweetRequest tweetRequest) {
        TweetResponse tweet = tweetMapper.createTweet(tweetRequest);
        messagingTemplate.convertAndSend("/topic/feed/add", tweet);
        messagingTemplate.convertAndSend("/topic/user/add/tweet/" + tweet.getUser().getId(), tweet);
        return ResponseEntity.ok(tweet);
    }

    @PostMapping("/poll")
    public ResponseEntity<TweetResponse> createPoll(@RequestBody TweetRequest tweetRequest) {
        TweetResponse tweet = tweetMapper.createPoll(tweetRequest);
        messagingTemplate.convertAndSend("/topic/feed/add", tweet);
        messagingTemplate.convertAndSend("/topic/user/add/tweet/" + tweet.getUser().getId(), tweet);
        return ResponseEntity.ok(tweet);
    }

    @DeleteMapping("/{tweetId}")
    public ResponseEntity<TweetResponse> deleteTweet(@PathVariable Long tweetId) {
        TweetResponse tweet = tweetMapper.deleteTweet(tweetId);
        messagingTemplate.convertAndSend("/topic/feed", tweet);
        messagingTemplate.convertAndSend("/topic/tweet/" + tweet.getId(), tweet);
        messagingTemplate.convertAndSend("/topic/user/update/tweet/" + tweet.getUser().getId(), tweet);
        return ResponseEntity.ok(tweet);
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<List<TweetResponse>> searchTweets(@PathVariable String text) {
        return ResponseEntity.ok(tweetMapper.searchTweets(text));
    }

    @GetMapping("/like/{tweetId}")
    public ResponseEntity<NotificationTweetResponse> likeTweet(@PathVariable Long tweetId) {
        NotificationResponse notification = tweetMapper.likeTweet(tweetId);

        if (notification.getId() != null) {
            messagingTemplate.convertAndSend("/topic/notifications/" + notification.getTweet().getUser().getId(), notification);
        }
        messagingTemplate.convertAndSend("/topic/feed", notification.getTweet());
        messagingTemplate.convertAndSend("/topic/tweet/" + notification.getTweet().getId(), notification.getTweet());
        messagingTemplate.convertAndSend("/topic/user/update/tweet/" + notification.getTweet().getUser().getId(), notification.getTweet());
        return ResponseEntity.ok(notification.getTweet());
    }

    @GetMapping("/retweet/{tweetId}")
    public ResponseEntity<NotificationTweetResponse> retweet(@PathVariable Long tweetId) {
        NotificationResponse notification = tweetMapper.retweet(tweetId);

        if (notification.getId() != null) {
            messagingTemplate.convertAndSend("/topic/notifications/" + notification.getTweet().getUser().getId(), notification);
        }
        messagingTemplate.convertAndSend("/topic/feed", notification.getTweet());
        messagingTemplate.convertAndSend("/topic/tweet/" + notification.getTweet().getId(), notification.getTweet());
        messagingTemplate.convertAndSend("/topic/user/update/tweet/" + notification.getTweet().getUser().getId(), notification.getTweet());
        return ResponseEntity.ok(notification.getTweet());
    }

    @PostMapping("/reply/{tweetId}")
    public ResponseEntity<TweetResponse> replyTweet(@PathVariable Long tweetId, @RequestBody TweetRequest tweetRequest) {
        TweetResponse tweet = tweetMapper.replyTweet(tweetId, tweetRequest);
        messagingTemplate.convertAndSend("/topic/feed", tweet);
        messagingTemplate.convertAndSend("/topic/tweet/" + tweet.getId(), tweet);
        messagingTemplate.convertAndSend("/topic/user/update/tweet/" + tweet.getUser().getId(), tweet);
        return ResponseEntity.ok(tweet);
    }

    @PostMapping("/quote/{tweetId}")
    public ResponseEntity<TweetResponse> quoteTweet(@PathVariable Long tweetId, @RequestBody TweetRequest tweetRequest) {
        TweetResponse tweet = tweetMapper.quoteTweet(tweetId, tweetRequest);
        messagingTemplate.convertAndSend("/topic/feed/add", tweet);
        messagingTemplate.convertAndSend("/topic/tweet/" + tweet.getId(), tweet);
        messagingTemplate.convertAndSend("/topic/user/add/tweet/" + tweet.getUser().getId(), tweet);
        return ResponseEntity.ok(tweet);
    }

    @GetMapping("/reply/change/{tweetId}")
    public ResponseEntity<TweetResponse> changeTweetReplyType(@PathVariable Long tweetId, @RequestParam ReplyType replyType) {
        TweetResponse tweet = tweetMapper.changeTweetReplyType(tweetId, replyType);
        messagingTemplate.convertAndSend("/topic/feed", tweet);
        messagingTemplate.convertAndSend("/topic/tweet/" + tweet.getId(), tweet);
        messagingTemplate.convertAndSend("/topic/user/update/tweet/" + tweet.getUser().getId(), tweet);
        return ResponseEntity.ok(tweet);
    }

    @PostMapping("/vote")
    public ResponseEntity<TweetResponse> voteInPoll(@RequestBody VoteRequest voteRequest) {
        TweetResponse tweet = tweetMapper.voteInPoll(voteRequest);
        messagingTemplate.convertAndSend("/topic/feed", tweet);
        messagingTemplate.convertAndSend("/topic/tweet/" + tweet.getId(), tweet);
        messagingTemplate.convertAndSend("/topic/user/update/tweet/" + tweet.getUser().getId(), tweet);
        return ResponseEntity.ok(tweet);
    }
}
