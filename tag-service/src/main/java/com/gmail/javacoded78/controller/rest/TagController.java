package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.TagResponse;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.UI_V1_TAGS;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_TAGS)
public class TagController {

    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getTags() {
        return ResponseEntity.ok(tagMapper.getTags());
    }

    @GetMapping("/trends")
    public ResponseEntity<List<TagResponse>> getTrends(@PageableDefault(size = 20) Pageable pageable) {
        HeaderResponse<TagResponse> response = tagMapper.getTrends(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TweetResponse>> getTweetsByTag(@RequestParam("tagName") String tagName) {
        return ResponseEntity.ok(tagMapper.getTweetsByTag(tagName));
    }
}
