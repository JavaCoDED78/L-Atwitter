package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.TweetRepository;
import com.gmail.javacoded78.latwitter.repository.UserRepository;
import com.gmail.javacoded78.latwitter.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Override
    public List<Tweet> getTweets() {
        return tweetRepository.findAllByOrderByDateTimeDesc();
    }

    @Override
    public Tweet getTweetById(Long tweetId) {
        return tweetRepository.getOne(tweetId);
    }

    @Override
    public List<Tweet> getTweetsByUser(User user) {
        return tweetRepository.findAllByUserOrderByDateTimeDesc(user);
    }

    @Override
    @Transactional
    public List<Tweet> createTweet(Tweet tweet) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        tweet.setUser(user);
        tweetRepository.save(tweet);
        List<Tweet> tweets = user.getTweets();
        tweets.add(tweet);
        return tweetRepository.findAllByUserOrderByDateTimeDesc(user);
    }

    @Override
    @Transactional
    public List<Tweet> deleteTweet(Long tweetId) {
        Tweet tweet = tweetRepository.getOne(tweetId);
        tweetRepository.delete(tweet);
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        List<Tweet> tweets = user.getTweets();
        tweets.remove(tweet);
        return tweetRepository.findAllByOrderByDateTimeDesc();
    }

    @Override
    public Tweet likeTweet(Long tweetId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        Tweet tweet = tweetRepository.getOne(tweetId);
        List<User> tweetLikes = tweet.getLikes();

        if (tweetLikes.contains(user)) {
            tweetLikes.remove(user);
        } else {
            tweetLikes.add(user);
        }
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet retweet(Long tweetId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(principal.getName());
        Tweet tweet = tweetRepository.getOne(tweetId);
        List<Tweet> tweets = user.getTweets();
        List<User> retweets = tweet.getRetweets();

        if (tweets.contains(tweet)) {
            tweets.remove(tweet);
            retweets.remove(user);
        } else {
            tweets.add(tweet);
            retweets.add(user);
        }
        return tweetRepository.save(tweet);
    }
}
