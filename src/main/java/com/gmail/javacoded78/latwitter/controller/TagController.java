package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.response.TagResponse;
import com.gmail.javacoded78.latwitter.dto.response.TweetResponse;
import com.gmail.javacoded78.latwitter.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/{tagName}")
    public ResponseEntity<List<TweetResponse>> getTweetsByTag(@PathVariable String tagName) {
        return ResponseEntity.ok(tagMapper.getTweetsByTag(tagName));
    }
}
