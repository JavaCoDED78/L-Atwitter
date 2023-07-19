package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.repository.TagRepository;
import com.gmail.javacoded78.latwitter.repository.TweetRepository;
import com.gmail.javacoded78.latwitter.repository.projection.TweetProjection;
import com.gmail.javacoded78.latwitter.repository.projection.tag.TagProjection;
import com.gmail.javacoded78.latwitter.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TweetRepository tweetRepository;

    @Override
    public List<TagProjection> getTags() {
        return tagRepository.findTop5ByOrderByTweetsQuantityDesc();
    }

    @Override
    public List<TagProjection> getTrends() {
        return tagRepository.findByOrderByTweetsQuantityDesc();
    }

    @Override
    public List<TweetProjection> getTweetsByTag(String tagName) {
        return tweetRepository.getTweetsByTagName(tagName).stream()
                .map(TweetsProjection::getTweet)
                .collect(Collectors.toList());
    }
}
