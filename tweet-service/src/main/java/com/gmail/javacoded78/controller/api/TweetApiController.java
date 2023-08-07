package com.gmail.javacoded78.controller.api;


import com.gmail.javacoded78.dto.response.chat.ChatTweetResponse;
import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationTweetResponse;
import com.gmail.javacoded78.mapper.TweetClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_TWEETS;
import static com.gmail.javacoded78.constants.PathConstants.CHAT_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.COUNT_TEXT;
import static com.gmail.javacoded78.constants.PathConstants.IDS;
import static com.gmail.javacoded78.constants.PathConstants.ID_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.NOTIFICATION_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.TAG_IDS;
import static com.gmail.javacoded78.constants.PathConstants.TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.USER_IDS;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_TWEETS)
public class TweetApiController {

    private final TweetClientMapper tweetClientMapper;

    @PostMapping(TAG_IDS)
    public List<TweetResponse> getTweetsByIds(@RequestBody IdsRequest requests) {
        return tweetClientMapper.getTweetsByIds(requests);
    }

    @PostMapping(USER_IDS)
    public HeaderResponse<TweetResponse> getTweetsByUserIds(@RequestBody IdsRequest request,
                                                            @SpringQueryMap Pageable pageable) {
        return tweetClientMapper.getTweetsByUserIds(request, pageable);
    }

    @GetMapping(TWEET_ID)
    public TweetResponse getTweetById(@PathVariable("tweetId") Long tweetId) {
        return tweetClientMapper.getTweetById(tweetId);
    }

    @PostMapping(IDS)
    public HeaderResponse<TweetResponse> getTweetsByIds(@RequestBody IdsRequest request, Pageable pageable) {
        return tweetClientMapper.getTweetsByIds(request, pageable);
    }

    @GetMapping(NOTIFICATION_TWEET_ID)
    public NotificationTweetResponse getNotificationTweet(@PathVariable("tweetId") Long tweetId) {
        return tweetClientMapper.getNotificationTweet(tweetId);
    }

    @GetMapping(ID_TWEET_ID)
    public Boolean isTweetExists(@PathVariable("tweetId") Long tweetId) {
        return tweetClientMapper.isTweetExists(tweetId);
    }

    @GetMapping(COUNT_TEXT)
    public Long getTweetCountByText(@PathVariable("text") String text) {
        return tweetClientMapper.getTweetCountByText(text);
    }

    @GetMapping(CHAT_TWEET_ID)
    public ChatTweetResponse getChatTweet(@PathVariable("tweetId") Long tweetId) {
        return tweetClientMapper.getChatTweet(tweetId);
    }
}
