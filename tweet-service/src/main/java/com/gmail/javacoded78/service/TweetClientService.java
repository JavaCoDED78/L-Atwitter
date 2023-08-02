package com.gmail.javacoded78.service;


import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.repository.projection.ChatTweetProjection;
import com.gmail.javacoded78.repository.projection.NotificationTweetProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TweetClientService {

    List<TweetProjection> getTweetsByIds(IdsRequest requests);

    Page<TweetProjection> getTweetsByUserIds(IdsRequest request, Pageable pageable);

    TweetProjection getTweetById(Long tweetId);

    Page<TweetProjection> getTweetsByIds(IdsRequest request, Pageable pageable);

    NotificationTweetProjection getNotificationTweet(Long tweetId);

    Boolean isTweetExists(Long tweetId);

    ChatTweetProjection getChatTweet(Long tweetId);
}
