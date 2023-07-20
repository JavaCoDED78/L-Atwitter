package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.response.TagResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagMapper {

    private final BasicMapper basicMapper;
    private final TagService tagService;

    public List<TagResponse> getTags() {
        return basicMapper.convertToResponseList(tagService.getTags(), TagResponse.class);
    }

    public List<TagResponse> getTrends() {
        return basicMapper.convertToResponseList(tagService.getTrends(), TagResponse.class);
    }

    public List<TweetResponse> getTweetsByTag(String tagName) {
        return basicMapper.convertToResponseList(tagService.getTweetsByTag(tagName), TweetResponse.class);
    }
}
