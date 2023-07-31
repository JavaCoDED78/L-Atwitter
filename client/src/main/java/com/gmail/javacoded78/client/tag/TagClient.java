package com.gmail.javacoded78.client.tag;

import com.gmail.javacoded78.common.configuration.FeignConfiguration;
import com.gmail.javacoded78.common.models.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.gmail.javacoded78.common.controller.PathConstants.API_V1_TAGS;

@FeignClient(value = "tag-service", configuration = FeignConfiguration.class)
public interface TagClient {

    @GetMapping(API_V1_TAGS + "/{tweetId}")
    List<Tag> getTagsByTweetId(@PathVariable("tweetId") Long tweetId);

    @GetMapping(API_V1_TAGS + "/search")
    Tag getTagByTagName(@RequestParam("tagName") String tagName);

    @PostMapping(API_V1_TAGS + "/save")
    Tag saveTag(@RequestBody Tag tag);

    @DeleteMapping(API_V1_TAGS + "/delete")
    void deleteTag(@RequestBody Tag tag);
}
