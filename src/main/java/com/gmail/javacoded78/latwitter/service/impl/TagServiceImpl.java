package com.gmail.javacoded78.latwitter.service.impl;

import com.gmail.javacoded78.latwitter.repository.TagRepository;
import com.gmail.javacoded78.latwitter.repository.TweetRepository;
import com.gmail.javacoded78.latwitter.repository.projection.tweet.TweetProjection;
import com.gmail.javacoded78.latwitter.repository.projection.tweet.TweetsProjection;
import com.gmail.javacoded78.latwitter.repository.projection.tag.TagProjection;
import com.gmail.javacoded78.latwitter.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<TagProjection> getTrends(Pageable pageable) {
        return tagRepository.findByOrderByTweetsQuantityDesc(pageable);
    }

    @Override
    public List<TweetProjection> getTweetsByTag(String tagName) {
        return tweetRepository.getTweetsByTagName(tagName).stream()
                .map(TweetsProjection::getTweet)
                .collect(Collectors.toList());
    }
}
