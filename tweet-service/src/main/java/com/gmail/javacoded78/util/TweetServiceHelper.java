package com.gmail.javacoded78.util;

import com.gmail.javacoded78.dto.response.chat.ChatTweetUserResponse;
import com.gmail.javacoded78.dto.request.NotificationRequest;
import com.gmail.javacoded78.dto.response.tweet.TweetAdditionalInfoUserResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetAuthorResponse;
import com.gmail.javacoded78.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.NotificationClient;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.repository.BookmarkRepository;
import com.gmail.javacoded78.repository.LikeTweetRepository;
import com.gmail.javacoded78.repository.RetweetRepository;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.RetweetProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.repository.projection.TweetUserProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TweetServiceHelper {

    private final TweetRepository tweetRepository;
    private final LikeTweetRepository likeTweetRepository;
    private final RetweetRepository retweetRepository;
    private final BookmarkRepository bookmarkRepository;
    private final NotificationClient notificationClient;
    private final UserClient userClient;

    public TweetProjection getTweetProjection(Long tweetId) {
        return tweetRepository.getTweetById(tweetId, TweetProjection.class).get();
    }

    public TweetUserProjection getTweetUserProjection(Long tweetId) {
        return tweetRepository.getTweetById(tweetId, TweetUserProjection.class).get();
    }

    public void checkIsUserExist(Long userId) {
        boolean isUserExist = userClient.isUserExists(userId);

        if (!isUserExist) {
            throw new ApiRequestException("User (id:" + userId + ") not found", HttpStatus.NOT_FOUND);
        }
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

    public boolean isMyProfileBlockedByUser(Long userId) {
        return userClient.isMyProfileBlockedByUser(userId);
    }

    public TweetAuthorResponse getTweetAuthor(Long userId) {
        return userClient.getTweetAuthor(userId);
    }

    public TweetAdditionalInfoUserResponse getTweetAdditionalInfoUser(Long userId) {
        return userClient.getTweetAdditionalInfoUser(userId);
    }

    public boolean isUserHavePrivateProfile(Long userId) {
        return userClient.isUserHavePrivateProfile(userId);
    }

    public void checkIsValidUserProfile(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();

        if (isUserHavePrivateProfile(userId) && !userId.equals(authUserId)) {
            throw new ApiRequestException("User not found", HttpStatus.BAD_REQUEST);
        }
        if (isMyProfileBlockedByUser(userId)) {
            throw new ApiRequestException("User profile blocked", HttpStatus.BAD_REQUEST);
        }
    }

    public ChatTweetUserResponse getChatTweetUser(Long userId) {
        return userClient.getChatTweetUser(userId);
    }

    public NotificationResponse sendNotification(NotificationType notificationType, boolean isTweetLiked, Long notifiedUserId,
                                                 Long userId, Long tweetId) {
        NotificationRequest request = NotificationRequest.builder()
                .notificationType(notificationType)
                .notificationCondition(isTweetLiked)
                .notifiedUserId(notifiedUserId)
                .userId(userId)
                .tweetId(tweetId)
                .build();
        return notificationClient.sendTweetNotification(request);
    }

    public <T> Page<T> getPageableTweetProjectionList(Pageable pageable, List<T> tweets, int totalPages) {
        PagedListHolder<T> page = new PagedListHolder<>(tweets);
        page.setPage(pageable.getPageNumber());
        page.setPageSize(pageable.getPageSize());
        return new PageImpl<>(page.getPageList(), pageable, totalPages);
    }

    public List<TweetUserProjection> combineTweetsArrays(List<TweetUserProjection> tweets,
                                                         List<RetweetProjection> retweets) {
        List<TweetUserProjection> allTweets = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < tweets.size() && j < retweets.size()) {
            if (tweets.get(i).getDateTime().isAfter(retweets.get(j).getRetweetDate())) {
                allTweets.add(tweets.get(i));
                i++;
            } else {
                allTweets.add(retweets.get(j).getTweet());
                j++;
            }
        }
        while (i < tweets.size()) {
            allTweets.add(tweets.get(i));
            i++;
        }
        while (j < retweets.size()) {
            allTweets.add(retweets.get(j).getTweet());
            j++;
        }
        return allTweets;
    }
}
