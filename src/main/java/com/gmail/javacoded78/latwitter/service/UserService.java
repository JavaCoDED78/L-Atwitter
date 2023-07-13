package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Image;
import com.gmail.javacoded78.latwitter.model.Notification;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User getUserById(Long userId);

    List<User> getUsers();

    List<User> getRelevantUsers();

    List<User> searchUsersByUsername(String username);

    User startUseTwitter(Long userId);

    List<Tweet> getUserTweets(Long userId);

    List<Tweet> getUserLikedTweets(Long userId);

    List<Tweet> getUserMediaTweets(Long userId);

    List<Tweet> getUserRetweetsAndReplies(Long userId);

    List<Notification> getUserNotifications();

    List<Tweet> getUserBookmarks();

    User processUserBookmarks(Long tweetId);

    Image uploadImage(MultipartFile multipartFile);

    User updateUserProfile(User userInfo);

    Notification processFollow(Long userId);

    User processPinTweet(Long tweetId);
}
