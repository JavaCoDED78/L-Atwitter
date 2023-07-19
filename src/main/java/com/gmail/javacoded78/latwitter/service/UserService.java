package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Image;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.projection.BookmarkProjection;
import com.gmail.javacoded78.latwitter.repository.projection.LikeTweetProjection;
import com.gmail.javacoded78.latwitter.repository.projection.TweetProjection;
import com.gmail.javacoded78.latwitter.repository.projection.TweetsProjection;
import com.gmail.javacoded78.latwitter.repository.projection.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserProfileProjection getUserById(Long userId);

    List<UserProjection> getUsers();

    List<UserProjection> getRelevantUsers();

    List<UserProjection> searchUsersByUsername(String username);

    Boolean startUseTwitter();

    Page<TweetProjection> getUserTweets(Long userId, Pageable pageable);

    Page<LikeTweetProjection> getUserLikedTweets(Long userId, Pageable pageable);

    Page<TweetProjection> getUserMediaTweets(Long userId, Pageable pageable);

    Page<TweetProjection> getUserRetweetsAndReplies(Long userId, Pageable pageable);

    Map<String, Object> getUserNotifications();

    Page<TweetsProjection> getNotificationsFromTweetAuthors(Pageable pageable);

    Page<BookmarkProjection> getUserBookmarks(Pageable pageable);

    Boolean processUserBookmarks(Long tweetId);

    Image uploadImage(MultipartFile multipartFile);

    AuthUserProjection updateUserProfile(User userInfo);

    List<BaseUserProjection> getFollowers(Long userId);

    List<BaseUserProjection> getFollowing(Long userId);

    Map<String, Object> processFollow(Long userId);

    List<BaseUserProjection> overallFollowers(Long userId);

    UserProfileProjection processFollowRequestToPrivateProfile(Long userId);

    String acceptFollowRequest(Long userId);

    String declineFollowRequest(Long userId);

    Boolean processSubscribeToNotifications(Long userId);

    Long processPinTweet(Long tweetId);

    List<BlockedUserProjection> getBlockList();

    Boolean processBlockList(Long userId);

    List<MutedUserProjection> getMutedList();

    Boolean processMutedList(Long userId);

    UserDetailProjection getUserDetails(Long userId);
}
