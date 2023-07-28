package com.gmail.javacoded78.service;

import com.gmail.javacoded78.models.Tag;

import java.util.List;

public interface TagClientService {

    List<Tag> getTagsByTweetId(Long tweetId);

    Tag getTagByTagName(String tagName);

    Tag saveTag(Tag tag);

    void deleteTag(Tag tag);
}
