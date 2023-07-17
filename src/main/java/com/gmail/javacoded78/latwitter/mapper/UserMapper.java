package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.request.SettingsRequest;
import com.gmail.javacoded78.latwitter.dto.request.UserRequest;
import com.gmail.javacoded78.latwitter.dto.response.AuthenticationResponse;
import com.gmail.javacoded78.latwitter.dto.response.ImageResponse;
import com.gmail.javacoded78.latwitter.dto.response.TweetHeaderResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationUserResponse;
import com.gmail.javacoded78.latwitter.dto.response.notification.NotificationsResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.dto.response.UserResponse;
import com.gmail.javacoded78.latwitter.model.Bookmark;
import com.gmail.javacoded78.latwitter.model.Image;
import com.gmail.javacoded78.latwitter.model.LikeTweet;
import com.gmail.javacoded78.latwitter.model.Notification;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.service.UserService;
import com.gmail.javacoded78.latwitter.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;
    private final TweetMapper tweetMapper;
    private final UserService userService;
    private final UserSettingsService userSettingsService;

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

    private List<UserResponse> convertUserListToResponse(List<User> users) {
        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
    }

    private List<NotificationUserResponse> convertUserListToNotificationResponse(Set<User> users) {
        return users.stream()
                .map(user -> modelMapper.map(user, NotificationUserResponse.class))
                .collect(Collectors.toList());
    }

    User convertToEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    private TweetHeaderResponse getTweetHeaderResponse(List<Tweet> tweets, Integer totalPages) {
        List<TweetResponse> tweetResponses = tweetMapper.convertListToResponse(tweets);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("page-total-count", String.valueOf(totalPages));
        return new TweetHeaderResponse(tweetResponses, responseHeaders);
    }

    public UserResponse getUserById(Long userId) {
        return convertToUserResponse(userService.getUserById(userId));
    }

    public List<UserResponse> getUsers() {
        return convertUserListToResponse(userService.getUsers());
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

    public UserResponse processSubscribeToNotifications(Long userId) {
        return convertToUserResponse(userService.processSubscribeToNotifications(userId));
    }

    public List<UserResponse> getRelevantUsers() {
        return convertUserListToResponse(userService.getRelevantUsers());
    }

    public List<UserResponse> searchUsersByUsername(String username) {
        return convertUserListToResponse(userService.searchUsersByUsername(username));
    }

    public UserResponse processPinTweet(Long tweetId) {
        return convertToUserResponse(userService.processPinTweet(tweetId));
    }

    @SuppressWarnings("unchecked")
    public NotificationsResponse getUserNotifications() {
        Map<String, Object> userNotifications = userService.getUserNotifications();
        NotificationsResponse notificationsResponse = new NotificationsResponse();
        notificationsResponse.setNotifications(convertListToNotificationResponse((List<Notification>) userNotifications.get("notifications")));
        notificationsResponse.setTweetAuthors(convertUserListToNotificationResponse((Set<User>) userNotifications.get("tweetAuthors")));
        return notificationsResponse;
    }

    public TweetHeaderResponse getNotificationsFromTweetAuthors(Pageable pageable) {
        Page<Tweet> tweets = userService.getNotificationsFromTweetAuthors(pageable);
        return getTweetHeaderResponse(tweets.getContent(), tweets.getTotalPages());
    }

    public UserResponse updateUsername(SettingsRequest request) {
        return convertToUserResponse(userSettingsService.updateUsername(request.getUsername()));
    }

    public AuthenticationResponse updateEmail(SettingsRequest request) {
        Map<String, Object> credentials = userSettingsService.updateEmail(request.getEmail());
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUser(convertToUserResponse((User) credentials.get("user")));
        response.setToken((String) credentials.get("token"));
        return response;
    }

    public UserResponse updatePhone(SettingsRequest request) {
        return convertToUserResponse(userSettingsService.updatePhone(request.getCountryCode(), request.getPhone()));
    }

    public UserResponse updateCountry(SettingsRequest request) {
        return convertToUserResponse(userSettingsService.updateCountry(request.getCountry()));
    }

    public UserResponse updateGender(SettingsRequest request) {
        return convertToUserResponse(userSettingsService.updateGender(request.getGender()));
    }

    public UserResponse updateLanguage(SettingsRequest request) {
        return convertToUserResponse(userSettingsService.updateLanguage(request.getLanguage()));
    }

    public UserResponse updateDirectMessageRequests(SettingsRequest request) {
        return convertToUserResponse(userSettingsService.updateDirectMessageRequests(request.isMutedDirectMessages()));
    }

    public UserResponse updatePrivateProfile(SettingsRequest request) {
        return convertToUserResponse(userSettingsService.updatePrivateProfile(request.isPrivateProfile()));
    }

    public UserResponse updateColorScheme(SettingsRequest request) {
        return convertToUserResponse(userSettingsService.updateColorScheme(request.getColorScheme()));
    }

    public UserResponse updateBackgroundColor(SettingsRequest request) {
        return convertToUserResponse(userSettingsService.updateBackgroundColor(request.getBackgroundColor()));
    }

    public List<UserResponse> getBlockList() {
        return convertUserListToResponse(userService.getBlockList());
    }

    public UserResponse processBlockList(Long userId) {
        return convertToUserResponse(userService.processBlockList(userId));
    }

    public List<UserResponse> getMutedList() {
        return convertUserListToResponse(userService.getMutedList());
    }

    public UserResponse processMutedList(Long userId) {
        return convertToUserResponse(userService.processMutedList(userId));
    }
}
