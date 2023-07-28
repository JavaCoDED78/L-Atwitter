package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.client.tweet.TweetClient;
import com.gmail.javacoded78.common.projection.TagProjection;
import com.gmail.javacoded78.common.projection.TweetProjection;
import com.gmail.javacoded78.repository.TagRepository;
import com.gmail.javacoded78.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TweetClient tweetClient;

    @Override
    public List<TagProjection> getTags() {
        return tagRepository.findTop5ByOrderByTweetsQuantityDesc();
    }

    @Override
    public Page<TagProjection> getTrends(Pageable pageable) {
        return tagRepository.findByOrderByTweetsQuantityDesc(pageable);
    }

    @Override
    public List<TweetProjection> getTweetsByTag(String tagName) {
        return tweetClient.getTweetsByTagName(tagName);
    }
}
