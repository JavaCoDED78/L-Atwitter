package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.UserResponse;
import com.gmail.javacoded78.dto.notification.NotificationResponse;
import com.gmail.javacoded78.repository.projection.TweetUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RetweetService {

    Page<TweetUserProjection> getUserRetweetsAndReplies(Long userId, Pageable pageable);

    HeaderResponse<UserResponse> getRetweetedUsersByTweetId(Long tweetId, Pageable pageable);

    NotificationResponse retweet(Long tweetId);
}
