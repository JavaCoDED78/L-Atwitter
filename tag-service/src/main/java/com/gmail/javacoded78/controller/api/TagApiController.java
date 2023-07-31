package com.gmail.javacoded78.controller.api;

import com.gmail.javacoded78.common.models.Tag;
import com.gmail.javacoded78.service.TagClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.common.controller.PathConstants.API_V1_TAGS;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_TAGS)
public class TagApiController {

    private final TagClientService tagClientService;

    @GetMapping("/parse/{text}/{tweetId}")
    public void parseHashtagsInText(@PathVariable("text") String text, @PathVariable("tweetId") Long tweetId) {
        tagClientService.parseHashtagsInText(text, tweetId);
    }

    @DeleteMapping("/delete/{tweetId}")
    public void deleteTagsByTweetId(@PathVariable("tweetId") Long tweetId) {
        tagClientService.deleteTagsByTweetId(tweetId);
    }
}
