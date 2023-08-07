package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.request.TweetRequest;
import com.gmail.javacoded78.dto.request.VoteRequest;
import com.gmail.javacoded78.feign.WebSocketClient;
import com.gmail.javacoded78.mapper.PollMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.javacoded78.constants.PathConstants.POLL;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.VOTE;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_FEED;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_FEED_ADD;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_TWEET;
import static com.gmail.javacoded78.constants.WebsocketConstants.TOPIC_USER_UPDATE_TWEET;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_TWEETS)
public class PollController {

    private final PollMapper pollMapper;
    private final WebSocketClient webSocketClient;

    @PostMapping(POLL)
    public ResponseEntity<TweetResponse> createPoll(@RequestBody TweetRequest tweetRequest) {
        TweetResponse tweet = pollMapper.createPoll(tweetRequest);
        webSocketClient.send(TOPIC_FEED_ADD, tweet);
        webSocketClient.send(TOPIC_USER_UPDATE_TWEET + tweet.getUser().getId(), tweet);
        return ResponseEntity.ok(tweet);
    }

    @PostMapping(VOTE) // TODO validate and fix
    public ResponseEntity<TweetResponse> voteInPoll(@RequestBody VoteRequest voteRequest) {
        TweetResponse tweet = pollMapper.voteInPoll(voteRequest);
        webSocketClient.send(TOPIC_FEED, tweet);
        webSocketClient.send(TOPIC_TWEET + tweet.getId(), tweet);
        webSocketClient.send(TOPIC_USER_UPDATE_TWEET + tweet.getUser().getId(), tweet);
        return ResponseEntity.ok(tweet);
    }
}
