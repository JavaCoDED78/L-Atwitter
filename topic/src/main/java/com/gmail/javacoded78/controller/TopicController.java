package com.gmail.javacoded78.controller;

import com.gmail.javacoded78.dto.request.SuggestedTopicsRequest;
import com.gmail.javacoded78.dto.request.TopicsCategoriesRequest;
import com.gmail.javacoded78.dto.response.TopicResponse;
import com.gmail.javacoded78.dto.response.TopicsByCategoriesResponse;
import com.gmail.javacoded78.mapper.TopicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topics")
public class TopicController {

    private final TopicMapper topicMapper;

    @PostMapping("/suggested")
    public ResponseEntity<List<TopicResponse>> getTopicsByIds(@RequestBody SuggestedTopicsRequest request) {
        return ResponseEntity.ok(topicMapper.getTopicsByIds(request.getTopicsIds()));
    }

    @PostMapping("/category")
    public ResponseEntity<List<TopicsByCategoriesResponse>> getTopicsByCategories(@RequestBody TopicsCategoriesRequest request) {
        return ResponseEntity.ok(topicMapper.getTopicsByCategories(request.getCategories()));
    }

    @GetMapping("/followed")
    public ResponseEntity<List<TopicResponse>> getFollowedTopics() {
        return ResponseEntity.ok(topicMapper.getFollowedTopics());
    }

    @GetMapping("/followed/{userId}")
    public ResponseEntity<List<TopicResponse>> getFollowedTopicsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(topicMapper.getFollowedTopicsByUserId(userId));
    }

    @GetMapping("/not_interested")
    public ResponseEntity<List<TopicResponse>> getNotInterestedTopics() {
        return ResponseEntity.ok(topicMapper.getNotInterestedTopics());
    }

    @GetMapping("/not_interested/{topicId}")
    public ResponseEntity<Boolean> processNotInterestedTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(topicMapper.processNotInterestedTopic(topicId));
    }

    @GetMapping("/follow/{topicId}")
    public ResponseEntity<Boolean> processFollowTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(topicMapper.processFollowTopic(topicId));
    }
}