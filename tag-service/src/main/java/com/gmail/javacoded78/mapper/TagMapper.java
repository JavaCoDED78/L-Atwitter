package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.common.dto.HeaderResponse;
import com.gmail.javacoded78.common.dto.TweetResponse;
import com.gmail.javacoded78.common.mapper.BasicMapper;
import com.gmail.javacoded78.dto.TagResponse;
import com.gmail.javacoded78.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    public HeaderResponse<TagResponse> getTrends(Pageable pageable) {
        return basicMapper.getHeaderResponse(tagService.getTrends(pageable), TagResponse.class);
    }

    public List<TweetResponse> getTweetsByTag(String tagName) {
        return tagService.getTweetsByTag(tagName);
    }
}
