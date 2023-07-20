package com.gmail.javacoded78.latwitter.mapper;

import com.gmail.javacoded78.latwitter.dto.response.TagResponse;
import com.gmail.javacoded78.latwitter.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.latwitter.repository.projection.tag.TagProjection;
import com.gmail.javacoded78.latwitter.service.TagService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TagMapper {

    private final ModelMapper modelMapper;
    private final TagService tagService;
    private final TweetMapper tweetMapper;

    private TagResponse convertToTagResponse(TagProjection tag) {
        return modelMapper.map(tag, TagResponse.class);
    }

    private List<TagResponse> convertTagsListToResponse(List<TagProjection> tags) {
        return tags.stream()
                .map(this::convertToTagResponse)
                .collect(Collectors.toList());
    }

    public List<TagResponse> getTags() {
        return convertTagsListToResponse(tagService.getTags());
    }

    public List<TagResponse> getTrends() {
        return convertTagsListToResponse(tagService.getTrends());
    }

    public List<TweetResponse> getTweetsByTag(String tagName) {
        return tweetMapper.convertProjectionListToResponseList(tagService.getTweetsByTag(tagName), TweetResponse.class);
    }
}
