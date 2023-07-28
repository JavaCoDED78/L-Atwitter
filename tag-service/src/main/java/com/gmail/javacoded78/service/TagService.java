package com.gmail.javacoded78.service;

import com.gmail.javacoded78.projection.TagProjection;
import com.gmail.javacoded78.projection.TweetProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    List<TagProjection> getTags();

    Page<TagProjection> getTrends(Pageable pageable);

    List<TweetProjection> getTweetsByTag(String tagName);
}
