package com.gmail.javacoded78.latwitter.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gmail.javacoded78.latwitter.exception.ApiRequestException;
import com.gmail.javacoded78.latwitter.model.Bookmark;
import com.gmail.javacoded78.latwitter.model.Image;
import com.gmail.javacoded78.latwitter.model.LikeTweet;
import com.gmail.javacoded78.latwitter.model.Notification;
import com.gmail.javacoded78.latwitter.model.NotificationType;
import com.gmail.javacoded78.latwitter.model.Retweet;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.BookmarkRepository;
import com.gmail.javacoded78.latwitter.repository.ImageRepository;
import com.gmail.javacoded78.latwitter.repository.LikeTweetRepository;
import com.gmail.javacoded78.latwitter.repository.NotificationRepository;
import com.gmail.javacoded78.latwitter.repository.RetweetRepository;
import com.gmail.javacoded78.latwitter.repository.TweetRepository;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.service.AuthenticationService;
import com.gmail.javacoded78.latwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final ImageRepository imageRepository;
    private final BookmarkRepository bookmarkRepository;
    private final RetweetRepository retweetRepository;
    private final LikeTweetRepository likeTweetRepository;
    private final NotificationRepository notificationRepository;
    private final AmazonS3 amazonS3client;

    @Value("${amazon.s3.bucket.name}")
    private String bucketName;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<User> getUsers() {
        User user = authenticationService.getAuthenticatedUser();
        return userRepository.findByActiveTrueAndIdNot(user.getId());
    }

    @Override
    public List<User> getRelevantUsers() {
        return userRepository.findTop5ByActiveTrue();
    }

    @Override
    public List<User> searchUsersByUsername(String text) {
        return userRepository.findByFullNameOrUsernameContainingIgnoreCase(text, text);
    }

    @Override
    public User startUseTwitter(Long userId) {
        User user = authenticationService.getAuthenticatedUser();
        user.setProfileStarted(true);
        return userRepository.save(user);
    }

    @Override
    public Page<Tweet> getUserTweets(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        List<Tweet> tweets = tweetRepository.findTweetsByUserId(user.getId());
        List<Retweet> retweets = retweetRepository.findRetweetsByUserId(user.getId());
        List<Tweet> userTweets = combineTweetsArrays(tweets, retweets);
        boolean isTweetExist = userTweets.removeIf(tweet -> tweet.equals(user.getPinnedTweet()));
        if (isTweetExist) {
            userTweets.add(0, user.getPinnedTweet());
        }
        return getPageableTweetList(pageable, userTweets, tweets.size() + retweets.size());
    }

    @Override
    public Page<Tweet> getUserRetweetsAndReplies(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        List<Tweet> replies = tweetRepository.findRepliesByUserId(user.getId());
        List<Retweet> retweets = retweetRepository.findRetweetsByUserId(user.getId());
        List<Tweet> userTweets = combineTweetsArrays(replies, retweets);
        return getPageableTweetList(pageable, userTweets, replies.size() + retweets.size());
    }

    @Override
    public Map<String, Object> getUserNotifications() {
        User user = authenticationService.getAuthenticatedUser();
        user.setNotificationsCount(0L);
        List<Notification> notifications = user.getNotifications().stream()
                .filter(notification -> !notification.getNotificationType().equals(NotificationType.TWEET))
                .sorted(Comparator.comparing(Notification::getDate).reversed())
                .collect(Collectors.toList());
        Set<User> tweetAuthors = user.getNotifications().stream()
                .filter(notification -> notification.getNotificationType().equals(NotificationType.TWEET)
                        && notification.getTweet().getUser().getSubscribers().contains(user))
                .map(notification -> notification.getTweet().getUser())
                .collect(Collectors.toSet());
        Map<String, Object> response = new HashMap<>();
        response.put("notifications", notifications);
        response.put("tweetAuthors", tweetAuthors);
        userRepository.save(user);
        return response;
    }

    @Override
    public Page<Tweet> getNotificationsFromTweetAuthors(Pageable pageable) {
        User user = authenticationService.getAuthenticatedUser();
        List<Tweet> tweets = user.getNotifications().stream()
                .filter(notification -> notification.getNotificationType().equals(NotificationType.TWEET))
                .sorted(Comparator.comparing(Notification::getDate).reversed())
                .map(Notification::getTweet)
                .collect(Collectors.toList());
        return getPageableTweetList(pageable, tweets, tweets.size());
    }

    @Override
    public Page<Bookmark> getUserBookmarks(Pageable pageable) {
        User user = authenticationService.getAuthenticatedUser();
        return bookmarkRepository.findByUser(user, pageable);
    }

    @Override
    public User processUserBookmarks(Long tweetId) {
        User user = authenticationService.getAuthenticatedUser();
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        List<Bookmark> bookmarks = user.getBookmarks();
        Optional<Bookmark> bookmark = bookmarks.stream()
                .filter(b -> b.getTweet().getId().equals(tweet.getId()))
                .findFirst();

        if (bookmark.isPresent()) {
            bookmarks.remove(bookmark.get());
            bookmarkRepository.delete(bookmark.get());
        } else {
            Bookmark newBookmark = new Bookmark();
            newBookmark.setTweet(tweet);
            newBookmark.setUser(user);
            bookmarkRepository.save(newBookmark);
            bookmarks.add(newBookmark);
        }

        return userRepository.save(user);
    }

    @Override
    public Page<LikeTweet> getUserLikedTweets(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        return likeTweetRepository.findByUserId(user.getId(), pageable);
    }

    @Override
    public Page<Tweet> getUserMediaTweets(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        return tweetRepository.findAllUserMediaTweets(user.getId(), pageable);
    }

    @Override
    public Image uploadImage(MultipartFile multipartFile) {
        Image image = new Image();
        if (multipartFile != null) {
            File file = new File(multipartFile.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
            amazonS3client.putObject(new PutObjectRequest(bucketName, fileName, file));
            image.setSrc(amazonS3client.getUrl(bucketName, fileName).toString());
            file.delete();
        }
        return imageRepository.save(image);
    }

    @Override
    public User updateUserProfile(User userInfo) {
        if (userInfo.getUsername().length() == 0 || userInfo.getUsername().length() > 50) {
            throw new ApiRequestException("Incorrect username length", HttpStatus.BAD_REQUEST);
        }
        User user = authenticationService.getAuthenticatedUser();

        if (userInfo.getAvatar() != null) {
            user.setAvatar(userInfo.getAvatar());
        }
        if (userInfo.getWallpaper() != null) {
            user.setWallpaper(userInfo.getWallpaper());
        }
        user.setUsername(userInfo.getUsername());
        user.setAbout(userInfo.getAbout());
        user.setLocation(userInfo.getLocation());
        user.setWebsite(userInfo.getWebsite());
        user.setProfileCustomized(true);
        return userRepository.save(user);
    }

    @Override
    public List<User> getFollowers(Long userId) {
        checkIsUserExist(userId);
        checkIsUserBlocked(userId);
        return userRepository.getFollowersById(userId);
    }

    @Override
    public List<User> getFollowing(Long userId) {
        checkIsUserExist(userId);
        checkIsUserBlocked(userId);
        return userRepository.getFollowingById(userId);
    }

    @Override
    public Notification processFollow(Long userId) {
        User user = authenticationService.getAuthenticatedUser();
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        List<User> followers = user.getFollowers();
        Optional<User> follower = followers.stream()
                .filter(f -> f.getId().equals(currentUser.getId()))
                .findFirst();

        if (follower.isPresent()) {
            followers.remove(follower.get());
            List<User> subscribers = currentUser.getSubscribers();
            Optional<User> subscriber = subscribers.stream()
                    .filter(s -> s.getId().equals(user.getId()))
                    .findFirst();
            subscriber.ifPresent(subscribers::remove);
        } else {
            followers.add(currentUser);
        }
        userRepository.save(user);

        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.FOLLOW);
        notification.setUser(user);
        notification.setUserToFollow(currentUser);

        if (!currentUser.getId().equals(user.getId())) {
            Optional<Notification> userNotification = currentUser.getNotifications().stream()
                    .filter(n -> n.getNotificationType().equals(NotificationType.FOLLOW)
                            && n.getUser().getId().equals(user.getId()))
                    .findFirst();

            if (userNotification.isEmpty()) {
                Notification newNotification = notificationRepository.save(notification);
                currentUser.setNotificationsCount(currentUser.getNotificationsCount() + 1);
                List<Notification> notifications = currentUser.getNotifications();
                notifications.add(newNotification);
                userRepository.save(currentUser);
                return newNotification;
            }
        }
        return notification;
    }

    @Override
    public List<User> overallFollowers(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        User user = authenticationService.getAuthenticatedUser();

        if (!user.getId().equals(userId)) {
            return user.getFollowers().stream()
                    .filter(follower -> currentUser.getFollowers().contains(follower))
                    .collect(Collectors.toList());
        } else {
            return user.getFollowers();
        }
    }

    @Override
    public User processFollowRequestToPrivateProfile(Long userId) {
        User user = authenticationService.getAuthenticatedUser();
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        List<User> followerRequests = currentUser.getFollowerRequests();
        Optional<User> followerRequest = currentUser.getFollowerRequests().stream()
                .filter(follower -> follower.getId().equals(user.getId()))
                .findFirst();

        if (followerRequest.isPresent()) {
            followerRequests.remove(followerRequest.get());
        } else {
            followerRequests.add(user);
        }
        return userRepository.save(currentUser);
    }

    @Override
    public User acceptFollowRequest(Long userId) {
        User user = authenticationService.getAuthenticatedUser();
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        user.getFollowerRequests().remove(currentUser);
        user.getFollowers().add(currentUser);
        return userRepository.save(user);
    }

    @Override
    public User declineFollowRequest(Long userId) {
        User user = authenticationService.getAuthenticatedUser();
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        user.getFollowerRequests().remove(currentUser);
        return userRepository.save(user);
    }

    @Override
    public User processSubscribeToNotifications(Long userId) {
        User user = authenticationService.getAuthenticatedUser();
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        return processUserList(currentUser, user, currentUser.getSubscribers());
    }

    @Override
    public User processPinTweet(Long tweetId) {
        User user = authenticationService.getAuthenticatedUser();
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));

        if (user.getPinnedTweet() == null || !user.getPinnedTweet().getId().equals(tweet.getId())) {
            user.setPinnedTweet(tweet);
        } else {
            user.setPinnedTweet(null);
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getBlockList() {
        User user = authenticationService.getAuthenticatedUser();
        return user.getUserBlockedList();
    }

    @Override
    public User processBlockList(Long userId) {
        User user = authenticationService.getAuthenticatedUser();
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        user.getFollowers().removeIf(follower -> follower.getId().equals(currentUser.getId()));
        user.getFollowing().removeIf(following -> following.getId().equals(currentUser.getId()));
        user.getUserLists().removeIf(list -> list.getMembers().stream()
                .anyMatch(member -> member.getId().equals(currentUser.getId())));
        return processUserList(user, currentUser, user.getUserBlockedList());
    }

    @Override
    public List<User> getMutedList() {
        User user = authenticationService.getAuthenticatedUser();
        return user.getUserMutedList();
    }

    @Override
    public User processMutedList(Long userId) {
        User user = authenticationService.getAuthenticatedUser();
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));
        return processUserList(user, currentUser, user.getUserMutedList());
    }

    private void checkIsUserExist(Long userId) {
        boolean userExist = userRepository.isUserExist(userId);

        if (!userExist) {
            throw new ApiRequestException("User (id:" + userId + ") not found", HttpStatus.NOT_FOUND);
        }
    }

    private void checkIsUserBlocked(Long userId) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        boolean userBlocked = userRepository.isUserBlocked(userId, authUserId);

        if (userBlocked) {
            throw new ApiRequestException("User (id:" + authUserId + ") is blocked", HttpStatus.BAD_REQUEST);
        }
    }

    private User processUserList(User authenticatedUser, User currentUser, List<User> userLists) {
        Optional<User> userFromList = userLists.stream()
                .filter(user -> user.getId().equals(currentUser.getId()))
                .findFirst();

        if (userFromList.isPresent()) {
            userLists.remove(userFromList.get());
        } else {
            userLists.add(currentUser);
        }
        return userRepository.save(authenticatedUser);
    }

    private Page<Tweet> getPageableTweetList(Pageable pageable, List<Tweet> tweets, int totalPages) {
        PagedListHolder<Tweet> page = new PagedListHolder<>(tweets);
        page.setPage(pageable.getPageNumber());
        page.setPageSize(pageable.getPageSize());
        return new PageImpl<>(page.getPageList(), pageable, totalPages);
    }

    private List<Tweet> combineTweetsArrays(List<Tweet> tweets, List<Retweet> retweets) {
        List<Tweet> allTweets = new ArrayList<>();
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
