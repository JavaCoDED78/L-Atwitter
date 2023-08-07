package com.gmail.javacoded78.controller.api;

import com.gmail.javacoded78.dto.request.TweetTextRequest;
import com.gmail.javacoded78.service.TagClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.API_V1_TAGS;
import static com.gmail.javacoded78.constants.PathConstants.DELETE_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.PARSE_TWEET_ID;
import static com.gmail.javacoded78.constants.PathConstants.SEARCH_TEXT;


@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_TAGS)
public class TagApiController {

    private final TagClientService tagClientService;

    @GetMapping(SEARCH_TEXT)
    public List<String> getTagsByText(@PathVariable("text") String text) {
        return tagClientService.getTagsByText(text);
    }

    @PostMapping(PARSE_TWEET_ID)
    public void parseHashtagsInText(@PathVariable("tweetId") Long tweetId, @RequestBody TweetTextRequest request) {
        tagClientService.parseHashtagsInText(tweetId, request.getText());
    }

    @DeleteMapping(DELETE_TWEET_ID)
    public void deleteTagsByTweetId(@PathVariable("tweetId") Long tweetId) {
        tagClientService.deleteTagsByTweetId(tweetId);
    }
}
