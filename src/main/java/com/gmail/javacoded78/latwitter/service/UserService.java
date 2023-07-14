package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Bookmark;
import com.gmail.javacoded78.latwitter.model.Image;
import com.gmail.javacoded78.latwitter.model.LikeTweet;
import com.gmail.javacoded78.latwitter.model.Notification;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User getUserById(Long userId);

    List<User> getUsers();

    List<User> getRelevantUsers();

    List<User> searchUsersByUsername(String username);

    User startUseTwitter(Long userId);

    Page<Tweet> getUserTweets(Long userId, Pageable pageable);

    Page<LikeTweet> getUserLikedTweets(Long userId, Pageable pageable);

    Page<Tweet> getUserMediaTweets(Long userId, Pageable pageable);

    Page<Tweet> getUserRetweetsAndReplies(Long userId, Pageable pageable);

    List<Notification> getUserNotifications();

    Page<Bookmark> getUserBookmarks(Pageable pageable);

    User processUserBookmarks(Long tweetId);

    Image uploadImage(MultipartFile multipartFile);

    User updateUserProfile(User userInfo);

    Notification processFollow(Long userId);

    User processPinTweet(Long tweetId);

    List<User> getBlockList();

    User processBlockList(Long userId);

    List<User> getMutedList();

    User processMutedList(Long userId);
}
