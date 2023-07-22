package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.response.HeaderResponse;
import com.gmail.javacoded78.latwitter.dto.response.TagResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getTags() {
        return ResponseEntity.ok(tagMapper.getTags());
    }

    @GetMapping("/trends")
    public ResponseEntity<List<TagResponse>> getTrends(@PageableDefault(size = 20) Pageable pageable) {
        HeaderResponse<TagResponse> response = tagMapper.getTrends(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TweetResponse>> getTweetsByTag(@RequestParam String tagName) {
        return ResponseEntity.ok(tagMapper.getTweetsByTag(tagName));
    }
}
