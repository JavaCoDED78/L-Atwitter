package com.gmail.javacoded78.latwitter.controller;

import com.gmail.javacoded78.latwitter.dto.response.TopicResponse;
import com.gmail.javacoded78.latwitter.mapper.TopicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topics")
public class TopicController {

    private final TopicMapper topicMapper;

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getTopics() {
        return ResponseEntity.ok(topicMapper.getTopics());
    }

    @GetMapping("/{topicCategory}")
    public ResponseEntity<List<TopicResponse>> getTopicsByCategory(@PathVariable String topicCategory) {
        return ResponseEntity.ok(topicMapper.getTopicsByCategory(topicCategory));
    }

    @GetMapping("/not_interested")
    public ResponseEntity<List<TopicResponse>> getNotInterestedTopics() {
        return ResponseEntity.ok(topicMapper.getNotInterestedTopics());
    }

    @GetMapping("/not_interested/{topicId}")
    public ResponseEntity<Boolean> addNotInterestedTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(topicMapper.addNotInterestedTopic(topicId));
    }

    @GetMapping("/follow/{topicId}")
    public ResponseEntity<Boolean> processFollowTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(topicMapper.processFollowTopic(topicId));
    }
}