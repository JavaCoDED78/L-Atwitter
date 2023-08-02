package com.gmail.javacoded78.service;

public interface TagClientService {

    void parseHashtagsInText(Long tweetId, String text);

    void deleteTagsByTweetId(Long tweetId);
}
