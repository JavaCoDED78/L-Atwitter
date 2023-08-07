package com.gmail.javacoded78.service;

import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    List<Tag> getTags();

    Page<Tag> getTrends(Pageable pageable);

    List<TweetResponse> getTweetsByTag(String tagName);
}
