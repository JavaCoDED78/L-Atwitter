package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.request.UserRequest;
import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import com.gmail.javacoded78.latwitter.dto.response.TweetHeaderResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.model.Bookmark;
import com.gmail.javacoded78.latwitter.model.Image;
import com.gmail.javacoded78.latwitter.model.LikeTweet;
import com.gmail.javacoded78.latwitter.model.Notification;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;
    private final TweetMapper tweetMapper;
    private final UserService userService;

    private ImageResponse convertToImageResponse(Image image) {
        return modelMapper.map(image, ImageResponse.class);
    }

    NotificationResponse convertToNotificationResponse(Notification notification) {
        return modelMapper.map(notification, NotificationResponse.class);
    }

    private List<NotificationResponse> convertListToNotificationResponse(List<Notification> notifications) {
        return notifications.stream()
                .map(this::convertToNotificationResponse)
                .collect(Collectors.toList());
    }

    UserResponse convertToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    private List<UserResponse> convertListToResponse(List<User> users) {
        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    User convertToEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    private TweetHeaderResponse getTweetHeaderResponse(List<Tweet> tweets, Integer totalPages) {
        List<TweetResponse> tweetResponses = tweetMapper.convertListToResponse((tweets));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("page-total-count", String.valueOf(totalPages));
        return new TweetHeaderResponse(tweetResponses, responseHeaders);
    }

    public UserResponse getUserById(Long userId) {
        return convertToUserResponse(userService.getUserById(userId));
    }

    public List<UserResponse> getUsers() {
        return convertListToResponse(userService.getUsers());
    }

    public ImageResponse uploadImage(MultipartFile multipartFile) {
        return convertToImageResponse(userService.uploadImage(multipartFile));
    }

    public UserResponse updateUserProfile(UserRequest userRequest) {
        return convertToUserResponse(userService.updateUserProfile(convertToEntity(userRequest)));
    }

    public UserResponse startUseTwitter(Long userId) {
        return convertToUserResponse(userService.startUseTwitter(userId));
    }

    public TweetHeaderResponse getUserTweets(Long userId, Pageable pageable) {
        Page<Tweet> userTweets = userService.getUserTweets(userId, pageable);
        return getTweetHeaderResponse(userTweets.getContent(), userTweets.getTotalPages());
    }

    public TweetHeaderResponse getUserLikedTweets(Long userId, Pageable pageable) {
        Page<LikeTweet> userLikedTweets = userService.getUserLikedTweets(userId, pageable);
        List<Tweet> tweets = new ArrayList<>();
        userLikedTweets.getContent().forEach(likeTweet -> tweets.add(likeTweet.getTweet()));
        return getTweetHeaderResponse(tweets, userLikedTweets.getTotalPages());
    }

    public TweetHeaderResponse getUserMediaTweets(Long userId, Pageable pageable) {
        Page<Tweet> mediaTweets = userService.getUserMediaTweets(userId, pageable);
        return getTweetHeaderResponse(mediaTweets.getContent(), mediaTweets.getTotalPages());
    }

    public TweetHeaderResponse getUserRetweetsAndReplies(Long userId, Pageable pageable) {
        Page<Tweet> retweetsAndReplies = userService.getUserRetweetsAndReplies(userId, pageable);
        return getTweetHeaderResponse(retweetsAndReplies.getContent(), retweetsAndReplies.getTotalPages());
    }

    public TweetHeaderResponse getUserBookmarks(Pageable pageable) {
        Page<Bookmark> bookmarks = userService.getUserBookmarks(pageable);
        List<Tweet> tweets = new ArrayList<>();
        bookmarks.getContent().forEach(bookmark -> tweets.add(bookmark.getTweet()));
        return getTweetHeaderResponse(tweets, bookmarks.getTotalPages());
    }

    public UserResponse processUserBookmarks(Long tweetId) {
        return convertToUserResponse(userService.processUserBookmarks(tweetId));
    }

    public NotificationResponse processFollow(Long userId) {
        return convertToNotificationResponse(userService.processFollow(userId));
    }

    public List<UserResponse> getRelevantUsers() {
        return convertListToResponse(userService.getRelevantUsers());
    }

    public List<UserResponse> searchUsersByUsername(String username) {
        return convertListToResponse(userService.searchUsersByUsername(username));
    }

    public UserResponse processPinTweet(Long tweetId) {
        return convertToUserResponse(userService.processPinTweet(tweetId));
    }

    public List<NotificationResponse> getUserNotifications() {
        return convertListToNotificationResponse(userService.getUserNotifications());
    }
}
