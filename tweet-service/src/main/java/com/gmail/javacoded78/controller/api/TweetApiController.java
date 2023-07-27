package com.gmail.javacoded78.controller.api;

import com.gmail.javacoded78.models.Tweet;
import com.gmail.javacoded78.projection.TweetImageProjection;
import com.gmail.javacoded78.projection.TweetProjection;
import com.gmail.javacoded78.projection.TweetsProjection;
import com.gmail.javacoded78.projection.TweetsUserProjection;
import com.gmail.javacoded78.service.TweetClientService;
import com.gmail.javacoded78.tweet.TweetPageableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tweets")
public class TweetApiController {

    private final TweetClientService tweetClientService;

    public Optional<Tweet> getTweetById(@PathVariable Long userId) {
        return tweetClientService.getTweetById(userId);
    }

    public List<TweetsUserProjection> getTweetsByUserId(@PathVariable Long userId) {
        return tweetClientService.getTweetsByUserId(userId);
    }

    public Optional<TweetsUserProjection> getPinnedTweetByUserId(@PathVariable Long userId) {
        return tweetClientService.getPinnedTweetByUserId(userId);
    }

    public Page<TweetProjection> getAllUserMediaTweets(@RequestBody TweetPageableRequest request) {
        return tweetClientService.getAllUserMediaTweets(request);
    }

    public Page<TweetProjection> getUserMentions(@RequestBody TweetPageableRequest request) {
        return tweetClientService.getUserMentions(request);
    }

    public List<TweetImageProjection> getUserTweetImages(@RequestBody TweetPageableRequest request) {
        return tweetClientService.getUserTweetImages(request);
    }

    public List<TweetsUserProjection> getRepliesByUserId(@PathVariable Long userId) {
        return tweetClientService.getRepliesByUserId(userId);
    }

    public List<TweetsProjection> getNotificationsFromTweetAuthors(@PathVariable Long userId) {
        return tweetClientService.getNotificationsFromTweetAuthors(userId);
    }
}
