package com.gmail.javacoded78.feign;

import com.gmail.javacoded78.configuration.FeignConfiguration;
import com.gmail.javacoded78.dto.request.TweetTextRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.gmail.javacoded78.constants.FeignConstants.TAG_SERVICE;
import static com.gmail.javacoded78.constants.PathConstants.API_V1_TAGS;

@FeignClient(value = TAG_SERVICE, configuration = FeignConfiguration.class)
public interface TagClient {

    @PostMapping(API_V1_TAGS + "/parse/{tweetId}")
    void parseHashtagsInText(@PathVariable("tweetId") Long tweetId, @RequestBody TweetTextRequest request);

    @DeleteMapping(API_V1_TAGS + "/delete/{tweetId}")
    void deleteTagsByTweetId(@PathVariable("tweetId") Long tweetId);
}
