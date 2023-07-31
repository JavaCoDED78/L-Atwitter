package com.gmail.javacoded78.service;


import com.gmail.javacoded78.common.models.Tag;

import java.util.List;

public interface TagClientService {

    void parseHashtagsInText(String text, Long tweetId);

    void deleteTagsByTweetId(Long tweetId);
}
