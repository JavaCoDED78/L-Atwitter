package com.gmail.javacoded78.client.tag;

import com.gmail.javacoded78.common.models.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("tag-service")
public interface TagClient {

    @GetMapping("/api/v1/tags/{tweetId}")
    List<Tag> getTagsByTweetId(@PathVariable("tweetId") Long tweetId);

    @GetMapping("/api/v1/tags/search")
    Tag getTagByTagName(@RequestParam("tagName") String tagName);

    @PostMapping("/api/v1/tags/save")
    Tag saveTag(@RequestBody Tag tag);

    @DeleteMapping("/api/v1/tags/delete")
    void deleteTag(@RequestBody Tag tag);
}
