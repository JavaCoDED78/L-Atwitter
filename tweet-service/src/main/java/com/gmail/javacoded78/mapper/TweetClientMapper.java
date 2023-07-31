package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.common.dto.TweetResponse;
import com.gmail.javacoded78.common.mapper.BasicMapper;
import com.gmail.javacoded78.common.projection.TweetProjection;
import com.gmail.javacoded78.service.TweetClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TweetClientMapper {

    private final BasicMapper basicMapper;
    private final TweetClientService tweetClientService;

    public List<TweetResponse> getTweetsByTagName(String tagName) {
        List<TweetProjection> tweets = tweetClientService.getTweetsByTagName(tagName);
        return basicMapper.convertToResponseList(tweets, TweetResponse.class);
    }
}
