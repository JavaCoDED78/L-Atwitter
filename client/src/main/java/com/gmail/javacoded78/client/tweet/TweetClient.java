package com.gmail.javacoded78.client.tweet;

import com.gmail.javacoded78.common.configuration.FeignConfiguration;
import com.gmail.javacoded78.common.dto.TweetResponse;
import com.gmail.javacoded78.common.models.Tweet;
import com.gmail.javacoded78.common.projection.TweetImageProjection;
import com.gmail.javacoded78.common.projection.TweetProjection;
import com.gmail.javacoded78.common.projection.TweetsProjection;
import com.gmail.javacoded78.common.projection.TweetsUserProjection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import static com.gmail.javacoded78.common.controller.PathConstants.API_V1_TWEETS;

@FeignClient(value = "tweet-service", configuration = FeignConfiguration.class)
public interface TweetClient {

    @GetMapping(API_V1_TWEETS + "/{userId}")
    Optional<Tweet> getTweetById(@PathVariable("userId") Long userId);

    @GetMapping(API_V1_TWEETS + "/user/{userId}")
    List<TweetsUserProjection> getTweetsByUserId(@PathVariable("userId") Long userId);

    @GetMapping(API_V1_TWEETS + "/pinned/{userId}")
    Optional<TweetsUserProjection> getPinnedTweetByUserId(@PathVariable("userId") Long userId);

    @PostMapping(API_V1_TWEETS + "/user/media")
    Page<TweetProjection> getAllUserMediaTweets(@RequestBody TweetPageableRequest request);

    @PostMapping(API_V1_TWEETS + "/user/mentions")
    Page<TweetProjection> getUserMentions(@RequestBody TweetPageableRequest request);

    @PostMapping(API_V1_TWEETS + "/user/images")
    List<TweetImageProjection> getUserTweetImages(@RequestBody TweetPageableRequest request);

    @GetMapping(API_V1_TWEETS + "/replies/{userId}")
    List<TweetsUserProjection> getRepliesByUserId(@PathVariable("userId") Long userId);

    @GetMapping(API_V1_TWEETS + "/notification/{userId}")
    List<TweetsProjection> getNotificationsFromTweetAuthors(@PathVariable("userId") Long userId);

    @GetMapping(API_V1_TWEETS + "/tag")
    List<TweetResponse> getTweetsByTagName(@RequestParam("tagName") String tagName);

    @GetMapping(API_V1_TWEETS + "/user/ids")
    Page<TweetProjection> getTweetsByUserIds(@RequestBody TweetUserIdsRequest request);
}
