package com.gmail.javacoded78.controller.api;

import com.gmail.javacoded78.client.tweet.TweetUserIdsRequest;
import com.gmail.javacoded78.common.dto.TweetResponse;
import com.gmail.javacoded78.common.models.Tweet;
import com.gmail.javacoded78.common.projection.TweetImageProjection;
import com.gmail.javacoded78.common.projection.TweetProjection;
import com.gmail.javacoded78.common.projection.TweetsUserProjection;
import com.gmail.javacoded78.mapper.TweetClientMapper;
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

import static com.gmail.javacoded78.common.controller.PathConstants.API_V1_TWEETS;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_TWEETS)
public class TweetApiController {

    private final TweetClientService tweetClientService;
    private final TweetClientMapper tweetClientMapper;

    @GetMapping("/{userId}")
    public Optional<Tweet> getTweetById(@PathVariable("userId") Long userId) {
        return tweetClientService.getTweetById(userId);
    }

    @GetMapping("/user/{userId}")
    public List<TweetsUserProjection> getTweetsByUserId(@PathVariable("userId") Long userId) {
        return tweetClientService.getTweetsByUserId(userId);
    }

    @GetMapping("/pinned/{userId}")
    public Optional<TweetsUserProjection> getPinnedTweetByUserId(@PathVariable("userId") Long userId) {
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
    public List<TweetsUserProjection> getRepliesByUserId(@PathVariable("userId") Long userId) {
        return tweetClientService.getRepliesByUserId(userId);
    }

    @GetMapping("/notification/{userId}")
    public List<TweetProjection> getNotificationsFromTweetAuthors(@PathVariable("userId") Long userId) {
        return tweetClientService.getNotificationsFromTweetAuthors(userId);
    }

    @GetMapping("/tag")
    public List<TweetResponse> getTweetsByTagName(@RequestParam("tagName") String tagName) {
        return tweetClientMapper.getTweetsByTagName(tagName);
    }

    @GetMapping("/user/ids")
    public Page<TweetProjection> getTweetsByUserIds(@RequestBody TweetUserIdsRequest request) {
        return tweetClientService.getTweetsByUserIds(request);
    }
}
