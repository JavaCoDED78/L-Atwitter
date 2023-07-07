package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.model.Tag;
import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.repository.TagRepository;
import com.gmail.javacoded78.latwitter.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findTop5ByOrderByTweetsQuantityDesc();
    }

    @Override
    public List<Tag> getTrends() {
        return tagRepository.findByOrderByTweetsQuantityDesc();
    }

    @Override
    public List<Tweet> getTweetsByTag(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName);
        return tag.getTweets();
    }
}
