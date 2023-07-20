package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.repository.projection.tweet.TweetProjection;
import com.gmail.javacoded78.latwitter.repository.projection.tag.TagProjection;

import java.util.List;

public interface TagService {

    List<TagProjection> getTags();

    List<TagProjection> getTrends();

    List<TweetProjection> getTweetsByTag(String tagName);
}
