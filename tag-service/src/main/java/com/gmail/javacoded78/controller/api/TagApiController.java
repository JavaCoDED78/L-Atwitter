package com.gmail.javacoded78.controller.api;

import com.gmail.javacoded78.models.Tag;
import com.gmail.javacoded78.service.TagClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagApiController {

    private final TagClientService tagClientService;

    @GetMapping("/{tweetId}")
    public List<Tag> getTagsByTweetId(@PathVariable Long tweetId) {
        return tagClientService.getTagsByTweetId(tweetId);
    }

    @GetMapping("/search")
    public Tag getTagByTagName(@RequestParam String tagName) {
        return tagClientService.getTagByTagName(tagName);
    }

    @PostMapping("/save")
    public Tag saveTag(@RequestBody Tag tag) {
        return tagClientService.saveTag(tag);
    }

    @PostMapping("/delete")
    public void deleteTag(@RequestBody Tag tag) {
        tagClientService.deleteTag(tag);
    }
}
