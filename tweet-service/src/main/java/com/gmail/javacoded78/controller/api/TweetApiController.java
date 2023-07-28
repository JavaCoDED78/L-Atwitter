package com.gmail.javacoded78.controller.api;

import com.gmail.javacoded78.client.tweet.TweetUserIdsRequest;
import com.gmail.javacoded78.models.Tweet;
import com.gmail.javacoded78.projection.TweetImageProjection;
import com.gmail.javacoded78.projection.TweetProjection;
import com.gmail.javacoded78.projection.TweetsUserProjection;
import com.gmail.javacoded78.service.TweetClientService;
import com.gmail.javacoded78.client.tweet.TweetPageableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tweets")
public class TweetApiController {

    private final TweetClientService tweetClientService;

    @GetMapping("/{userId}")
    public Optional<Tweet> getTweetById(@PathVariable Long userId) {
        return tweetClientService.getTweetById(userId);
    }

    @GetMapping("/user/{userId}")
    public List<TweetsUserProjection> getTweetsByUserId(@PathVariable Long userId) {
        return tweetClientService.getTweetsByUserId(userId);
    }

    @GetMapping("/pinned/{userId}")
    public Optional<TweetsUserProjection> getPinnedTweetByUserId(@PathVariable Long userId) {
        return tweetClientService.getPinnedTweetByUserId(userId);
    }

    @PostMapping("/user/media")
    public Page<TweetProjection> getAllUserMediaTweets(@RequestBody TweetPageableRequest request) {
        return tweetClientService.getAllUserMediaTweets(request);
    }

    @PostMapping("/user/mentions")
    public Page<TweetProjection> getUserMentions(@RequestBody TweetPageableRequest request) {
        return tweetClientService.getUserMentions(request);
    }

    @PostMapping("/user/images")
    public List<TweetImageProjection> getUserTweetImages(@RequestBody TweetPageableRequest request) {
        return tweetClientService.getUserTweetImages(request);
    }

    @GetMapping("/replies/{userId}")
    public List<TweetsUserProjection> getRepliesByUserId(@PathVariable Long userId) {
        return tweetClientService.getRepliesByUserId(userId);
    }

    @GetMapping("/notification/{userId}")
    public List<TweetProjection> getNotificationsFromTweetAuthors(@PathVariable Long userId) {
        return tweetClientService.getNotificationsFromTweetAuthors(userId);
    }

    @GetMapping("/tag")
    public List<TweetProjection> getTweetsByTagName(@RequestParam String tagName) {
        return tweetClientService.getTweetsByTagName(tagName);
    }

    @GetMapping("/user/ids")
    public Page<TweetProjection> getTweetsByUserIds(@RequestBody TweetUserIdsRequest request) {
        return tweetClientService.getTweetsByUserIds(request);
    }
}
