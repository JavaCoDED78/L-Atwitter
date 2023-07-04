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
    @Transactional
    public List<Tweet> createTweet(Tweet tweet, MultipartFile multipartFile) {
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
        return tweetRepository.findAllByUserOrderByDateTimeDesc(user);
    }
}
