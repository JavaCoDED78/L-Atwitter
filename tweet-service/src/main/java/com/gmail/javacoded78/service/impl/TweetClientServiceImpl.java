package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.ChatTweetProjection;
import com.gmail.javacoded78.repository.projection.NotificationTweetProjection;
import com.gmail.javacoded78.repository.projection.TweetProjection;
import com.gmail.javacoded78.service.TweetClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TweetClientServiceImpl implements TweetClientService {

    private final TweetRepository tweetRepository;

    @Override
    public List<TweetProjection> getTweetsByIds(IdsRequest requests) {
        return tweetRepository.getTweetListsByIds(requests.getIds());
    }

    @Override
    public Page<TweetProjection> getTweetsByUserIds(IdsRequest request, Pageable pageable) {
        return tweetRepository.getTweetsByAuthorIds(request.getIds(), pageable);
    }

    @Override
    public TweetProjection getTweetById(Long tweetId) {
        return tweetRepository.getTweetById(tweetId, TweetProjection.class).orElse(null);
    }

    @Override
    public Page<TweetProjection> getTweetsByIds(IdsRequest request, Pageable pageable) {
        return tweetRepository.getTweetsByIds(request.getIds(), pageable);
    }

    @Override
    public NotificationTweetProjection getNotificationTweet(Long tweetId) {
        return tweetRepository.getTweetById(tweetId, NotificationTweetProjection.class).orElse(null);
    }

    @Override
    public Boolean isTweetExists(Long tweetId) {
        return tweetRepository.isTweetExists(tweetId);
    }

    @Override
    public Long getTweetCountByText(String text) {
        return tweetRepository.getTweetCountByText(text);
    }

    @Override
    public ChatTweetProjection getChatTweet(Long tweetId) {
        return tweetRepository.getTweetById(tweetId, ChatTweetProjection.class).orElse(null);
    }
}
