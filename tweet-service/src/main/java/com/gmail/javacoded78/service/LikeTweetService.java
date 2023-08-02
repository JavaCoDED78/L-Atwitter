package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.user.UserResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.repository.projection.LikeTweetProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeTweetService {

    Page<LikeTweetProjection> getUserLikedTweets(Long userId, Pageable pageable);

    HeaderResponse<UserResponse> getLikedUsersByTweetId(Long tweetId, Pageable pageable);

    NotificationResponse likeTweet(Long tweetId);
}
