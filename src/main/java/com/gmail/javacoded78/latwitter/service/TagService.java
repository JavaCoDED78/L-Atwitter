package com.gmail.javacoded78.latwitter.service;

import com.gmail.javacoded78.latwitter.model.Tag;
import com.gmail.javacoded78.latwitter.model.Tweet;

import java.util.List;

public interface TagService {

    List<Tag> getTags();

    List<Tag> getTrends();

    List<Tweet> getTweetsByTag(String tagName);
}
