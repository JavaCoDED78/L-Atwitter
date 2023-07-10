package com.gmail.javacoded78.latwitter.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gmail.javacoded78.latwitter.model.Image;
import com.gmail.javacoded78.latwitter.model.LikeTweet;
import com.gmail.javacoded78.latwitter.model.Retweet;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.ImageRepository;
import com.gmail.javacoded78.latwitter.repository.TweetRepository;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3 amazonS3client;

    @Value("${amazon.s3.bucket.name}")
    private String bucketName;

    @Override
    public User getUserById(Long userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public List<User> getUsers() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        List<User> users = userRepository.findAll();
        users.remove(user);
        return users;
    }

    @Override
    public List<User> getRelevantUsers() {
        return userRepository.findTop5By();
    }

    @Override
    public List<User> searchUsersByUsername(String text) {
        return userRepository.findByFullNameOrUsernameContaining(text, text);
    }

    @Override
    public User startUseTwitter(Long userId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        user.setProfileStarted(true);
        return userRepository.save(user);
    }

    @Override
    public List<Tweet> getUserTweets(Long userId) {
        User user = userRepository.getOne(userId);
        List<Tweet> tweets = user.getTweets().stream()
                .filter(tweet -> tweet.getAddressedUsername() == null)
                .sorted(Comparator.comparing(Tweet::getDateTime).reversed())
                .collect(Collectors.toList());
        List<Retweet> retweets = user.getRetweets();
        retweets.sort(Comparator.comparing(Retweet::getRetweetDate).reversed());
        List<Tweet> userTweets = combineTweetsArrays(tweets, retweets);
        boolean isTweetExist = userTweets.removeIf(tweet -> tweet.equals(user.getPinnedTweet()));
        if (isTweetExist) {
            userTweets.add(0, user.getPinnedTweet());
        }
        return userTweets;
    }

    @Override
    public List<Tweet> getUserRetweetsAndReplies(Long userId) {
        User user = userRepository.getOne(userId);
        List<Tweet> replies = user.getTweets().stream()
                .filter(tweet -> tweet.getAddressedUsername() != null)
                .sorted(Comparator.comparing(Tweet::getDateTime).reversed())
                .collect(Collectors.toList());
        List<Retweet> retweets = user.getRetweets();
        retweets.sort(Comparator.comparing(Retweet::getRetweetDate).reversed());
        return combineTweetsArrays(replies, retweets);
    }

    @Override
    public List<Tweet> getUserLikedTweets(Long userId) {
        User user = userRepository.getOne(userId);
        List<LikeTweet> likedTweets = user.getLikedTweets();
        likedTweets.sort(Comparator.comparing(LikeTweet::getLikeTweetDate).reversed());
        List<Tweet> allTweets = new ArrayList<>();
        likedTweets.forEach(likeTweet -> allTweets.add(likeTweet.getTweet()));
        return allTweets;
    }

    @Override
    public List<Tweet> getUserMediaTweets(Long userId) {
        User user = userRepository.getOne(userId);
        return tweetRepository.findByImagesIsNotNullAndUserOrderByDateTimeDesc(user);
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
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());

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
    public User follow(Long userId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        User currentUser = userRepository.getOne(userId);
        user.getFollowers().add(currentUser);
        userRepository.save(user);
        return currentUser;
    }

    @Override
    public User unfollow(Long userId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        User currentUser = userRepository.getOne(userId);
        user.getFollowers().remove(currentUser);
        userRepository.save(user);
        return currentUser;
    }

    @Override
    public User pinTweet(Long tweetId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        Tweet tweet = tweetRepository.getOne(tweetId);
        user.setPinnedTweet(tweet);
        userRepository.save(user);
        return userRepository.save(user);
    }

    @Override
    public User unpinTweet(Long tweetId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        user.setPinnedTweet(null);
        return userRepository.save(user);
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
