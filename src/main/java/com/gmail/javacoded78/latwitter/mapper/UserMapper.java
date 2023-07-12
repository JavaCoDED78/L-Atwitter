package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.request.UserRequest;
import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.model.Image;
import com.gmail.javacoded78.latwitter.model.Notification;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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

    public List<TweetResponse> getUserTweets(Long userId) {
        return tweetMapper.convertListToResponse(userService.getUserTweets(userId));
    }

    public List<TweetResponse> getUserLikedTweets(Long userId) {
        return tweetMapper.convertListToResponse(userService.getUserLikedTweets(userId));
    }

    public List<TweetResponse> getUserMediaTweets(Long userId) {
        return tweetMapper.convertListToResponse(userService.getUserMediaTweets(userId));
    }

    public List<TweetResponse> getUserRetweetsAndReplies(Long userId) {
        return tweetMapper.convertListToResponse(userService.getUserRetweetsAndReplies(userId));
    }

    public List<TweetResponse> getUserBookmarks() {
        return tweetMapper.convertListToResponse(userService.getUserBookmarks());
    }

    public UserResponse processUserBookmarks(Long tweetId) {
        return convertToUserResponse(userService.processUserBookmarks(tweetId));
    }

    public NotificationResponse follow(Long userId) {
        return convertToNotificationResponse(userService.follow(userId));
    }

    public List<UserResponse> getRelevantUsers() {
        return convertListToResponse(userService.getRelevantUsers());
    }

    public List<UserResponse> searchUsersByUsername(String username) {
        return convertListToResponse(userService.searchUsersByUsername(username));
    }

    public UserResponse pinTweet(Long tweetId) {
        return convertToUserResponse(userService.pinTweet(tweetId));
    }

    public UserResponse unpinTweet(Long tweetId) {
        return convertToUserResponse(userService.unpinTweet(tweetId));
    }

    public List<NotificationResponse> getUserNotifications() {
        return convertListToNotificationResponse(userService.getUserNotifications());
    }
}
