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

import static com.gmail.javacoded78.constants.PathConstants.CATEGORY;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOWED;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOWED_USER_ID;
import static com.gmail.javacoded78.constants.PathConstants.FOLLOW_TOPIC_ID;
import static com.gmail.javacoded78.constants.PathConstants.NOT_INTERESTED;
import static com.gmail.javacoded78.constants.PathConstants.NOT_INTERESTED_TOPIC_ID;
import static com.gmail.javacoded78.constants.PathConstants.SUGGESTED;
import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TOPICS;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_TOPICS)
public class TopicController {

    private final TopicMapper topicMapper;

    @PostMapping(SUGGESTED)
    public ResponseEntity<List<TopicResponse>> getTopicsByIds(@RequestBody SuggestedTopicsRequest request) {
        return ResponseEntity.ok(topicMapper.getTopicsByIds(request.getTopicsIds()));
    }

    @PostMapping(CATEGORY)
    public ResponseEntity<List<TopicsByCategoriesResponse>> getTopicsByCategories(@RequestBody TopicsCategoriesRequest request) {
        return ResponseEntity.ok(topicMapper.getTopicsByCategories(request.getCategories()));
    }

    @GetMapping(FOLLOWED)
    public ResponseEntity<List<TopicResponse>> getFollowedTopics() {
        return ResponseEntity.ok(topicMapper.getFollowedTopics());
    }

    @GetMapping(FOLLOWED_USER_ID)
    public ResponseEntity<List<TopicResponse>> getFollowedTopicsByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(topicMapper.getFollowedTopicsByUserId(userId));
    }

    @GetMapping(NOT_INTERESTED)
    public ResponseEntity<List<TopicResponse>> getNotInterestedTopics() {
        return ResponseEntity.ok(topicMapper.getNotInterestedTopics());
    }

    @GetMapping(NOT_INTERESTED_TOPIC_ID)
    public ResponseEntity<Boolean> processNotInterestedTopic(@PathVariable("topicId") Long topicId) {
        return ResponseEntity.ok(topicMapper.processNotInterestedTopic(topicId));
    }

    @GetMapping(FOLLOW_TOPIC_ID)
    public ResponseEntity<Boolean> processFollowTopic(@PathVariable("topicId") Long topicId) {
        return ResponseEntity.ok(topicMapper.processFollowTopic(topicId));
    }
}