package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.dto.request.IdsRequest;
import com.gmail.javacoded78.dto.response.tweet.TweetResponse;
import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.TweetClient;
import com.gmail.javacoded78.model.Tag;
import com.gmail.javacoded78.repository.TagRepository;
import com.gmail.javacoded78.repository.TweetTagRepository;
import com.gmail.javacoded78.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.TAG_NOT_FOUND;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    public static final String TEST_TAG = "test_tag";
    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TweetTagRepository tweetTagRepository;

    @Mock
    private TweetClient tweetClient;

    @Test
    void getTags() {
        when(tagRepository.findTop5ByOrderByTweetsQuantityDesc()).thenReturn(Arrays.asList(new Tag(), new Tag()));
        List<Tag> actualTags = tagService.getTags();
        assertThat(actualTags).hasSize(2);
        verify(tagRepository, times(1)).findTop5ByOrderByTweetsQuantityDesc();
    }

    @Test
    void getTrends() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Tag> expectedTags = new PageImpl<>(Arrays.asList(new Tag(), new Tag()), pageable, 20);
        when(tagRepository.findByOrderByTweetsQuantityDesc(pageable)).thenReturn(expectedTags);
        Page<Tag> actualTags = tagService.getTrends(pageable);
        assertThat(actualTags.getContent()).hasSize(2);
        verify(tagRepository, times(1)).findByOrderByTweetsQuantityDesc(pageable);
    }

    @Test
    void getTweetsByTag() {
        Tag tag = Tag.builder()
                .id(1L)
                .tagName(TEST_TAG)
                .tweetsQuantity(111L)
                .build();
        List<Long> tweetIds = Arrays.asList(1L, 2L);
        when(tagRepository.findByTagName(TEST_TAG)).thenReturn(Optional.of(tag));
        when(tweetTagRepository.getTweetIdsByTagId(tag.getId())).thenReturn(tweetIds);
        when(tweetClient.getTweetsByIds(new IdsRequest(tweetIds))).thenReturn(Arrays.asList(new TweetResponse(), new TweetResponse()));
        List<TweetResponse> actualTweetsResponse = tagService.getTweetsByTag(TEST_TAG);
        assertThat(actualTweetsResponse).hasSize(2);
        verify(tagRepository, times(1)).findByTagName(TEST_TAG);
        verify(tweetTagRepository, times(1)).getTweetIdsByTagId(tag.getId());
        verify(tweetClient, times(1)).getTweetsByIds(new IdsRequest(tweetIds));
    }

    @Test
    void getTweetsByTag_TagNotFound() {
        when(tagRepository.findByTagName(TEST_TAG)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> tagService.getTweetsByTag(TEST_TAG));
        assertThat(exception.getMessage()).isEqualTo(TAG_NOT_FOUND);
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(tagRepository, times(1)).findByTagName(TEST_TAG);
    }
}
