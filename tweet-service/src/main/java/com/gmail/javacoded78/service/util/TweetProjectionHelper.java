package com.gmail.javacoded78.service.util;

import com.gmail.javacoded78.dto.response.chat.ChatTweetUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetAdditionalInfoUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetAuthorResponse;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.repository.BookmarkRepository;
import com.gmail.javacoded78.repository.LikeTweetRepository;
import com.gmail.javacoded78.repository.RetweetRepository;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.repository.projection.TweetUserProjection;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TweetProjectionHelper {

    private final TweetRepository tweetRepository;
    private final LikeTweetRepository likeTweetRepository;
    private final RetweetRepository retweetRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserClient userClient;

    public TweetProjection getTweetProjection(Long tweetId) {
        return tweetRepository.getTweetById(tweetId, TweetProjection.class).get();
    }

    public TweetUserProjection getTweetUserProjection(Long tweetId) {
        return tweetRepository.getTweetById(tweetId, TweetUserProjection.class).get();
    }

    public boolean isUserLikedTweet(Long tweetId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return likeTweetRepository.isUserLikedTweet(authUserId, tweetId);
    }

    public boolean isUserRetweetedTweet(Long tweetId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return retweetRepository.isUserRetweetedTweet(authUserId, tweetId);
    }

    public boolean isUserBookmarkedTweet(Long tweetId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return bookmarkRepository.isUserBookmarkedTweet(authUserId, tweetId);
    }

    public boolean isUserFollowByOtherUser(Long userId) {
        return userClient.isUserFollowByOtherUser(userId);
    }

    public TweetAuthorResponse getTweetAuthor(Long userId) {
        return userClient.getTweetAuthor(userId);
    }

    public TweetAdditionalInfoUserResponse getTweetAdditionalInfoUser(Long userId) {
        return userClient.getTweetAdditionalInfoUser(userId);
    }

    public ChatTweetUserResponse getChatTweetUser(Long userId) {
        return userClient.getChatTweetUser(userId);
    }
}
