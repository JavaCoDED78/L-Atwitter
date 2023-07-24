package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.exception.ApiRequestException;
import com.gmail.javacoded78.latwitter.model.Topic;
import com.gmail.javacoded78.latwitter.model.User;
import com.gmail.javacoded78.latwitter.repository.TopicRepository;
import com.gmail.javacoded78.latwitter.service.AuthenticationService;
import com.gmail.javacoded78.latwitter.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final AuthenticationService authenticationService;
    private final TopicRepository topicRepository;

    @Override
    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

    @Override
    public List<Topic> getTopicsByCategory(String topicCategory) {
        return topicRepository.getTopicsByCategory(topicCategory);
    }

    @Override
    public List<Topic> getNotInterestedTopics() {
        User user = authenticationService.getAuthenticatedUser();
        return user.getNotInterestedTopics();
    }

    @Override
    @Transactional
    public Boolean addNotInterestedTopic(Long topicId) {
        User user = authenticationService.getAuthenticatedUser();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ApiRequestException("Topic not found", HttpStatus.NOT_FOUND));
        List<Topic> notInterestedTopics = user.getNotInterestedTopics();
        notInterestedTopics.add(topic);
        return true;
    }

    @Override
    @Transactional
    public Boolean processFollowTopic(Long topicId) {
        User user = authenticationService.getAuthenticatedUser();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ApiRequestException("Topic not found", HttpStatus.NOT_FOUND));
        List<Topic> notInterestedTopics = user.getNotInterestedTopics();
        Optional<Topic> topicFromList = notInterestedTopics.stream()
                .filter(notInterestedTopic -> notInterestedTopic.getId().equals(topic.getId()))
                .findFirst();

        if (topicFromList.isPresent()) {
            notInterestedTopics.remove(topicFromList.get());
            return false;
        } else {
            notInterestedTopics.add(topic);
            return true;
        }
    }
}
