package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.TweetResponse;
import com.gmail.javacoded78.dto.UserResponse;
import com.gmail.javacoded78.dto.notification.NotificationResponse;
import com.gmail.javacoded78.repository.projection.LikeTweetProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.service.LikeTweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeTweetMapper {

    private final BasicMapper basicMapper;
    private final LikeTweetService likeTweetService;

    public HeaderResponse<TweetResponse> getUserLikedTweets(Long userId, Pageable pageable) {
        Page<LikeTweetProjection> userLikedTweets = likeTweetService.getUserLikedTweets(userId, pageable);
        List<TweetProjection> tweets = new ArrayList<>();
        userLikedTweets.getContent().forEach(likeTweet -> tweets.add(likeTweet.getTweet()));
        return basicMapper.getHeaderResponse(tweets, userLikedTweets.getTotalPages(), TweetResponse.class);
    }

    public HeaderResponse<UserResponse> getLikedUsersByTweetId(Long tweetId, Pageable pageable) {
        return likeTweetService.getLikedUsersByTweetId(tweetId, pageable);
    }

    public NotificationResponse likeTweet(Long tweetId) {
        return likeTweetService.likeTweet(tweetId);
    }
}