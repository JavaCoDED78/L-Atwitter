package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.TweetResponse;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.TweetClient;
import com.gmail.javacoded78.model.Tag;
import com.gmail.javacoded78.repository.TagRepository;
import com.gmail.javacoded78.repository.TweetTagRepository;
import com.gmail.javacoded78.repository.projection.TagProjection;
import com.gmail.javacoded78.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TweetTagRepository tweetTagRepository;
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
    public List<TweetResponse> getTweetsByTag(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName)
                .orElseThrow(() -> new ApiRequestException("Tag not found", HttpStatus.NOT_FOUND));
        List<Long> tweetIds = tweetTagRepository.getTweetIdsByTagId(tag.getId());
        return tweetClient.getTweetsByIds(tweetIds);
    }
}
