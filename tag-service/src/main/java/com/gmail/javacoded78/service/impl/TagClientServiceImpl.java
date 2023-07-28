package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.common.models.Tag;
import com.gmail.javacoded78.repository.TagRepository;
import com.gmail.javacoded78.service.TagClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagClientServiceImpl implements TagClientService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTagsByTweetId(Long tweetId) {
        return tagRepository.findByTweets_Id(tweetId);
    }

    @Override
    public Tag getTagByTagName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }
}
