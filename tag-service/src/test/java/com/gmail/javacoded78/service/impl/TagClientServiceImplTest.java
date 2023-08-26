package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.model.Tag;
import com.gmail.javacoded78.repository.TagRepository;
import com.gmail.javacoded78.repository.TweetTagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagClientServiceImplTest {

    public static final String MOCK_TEXT = "test_text";
    public static final String HASHTAG_1 = "#hashtag1";
    public static final String HASHTAG_2 = "#hashtag2";
    @InjectMocks
    private TagClientServiceImpl tagClientService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TweetTagRepository tweetTagRepository;

    private Tag tag1;
    private Tag tag2;

    @BeforeEach
    public void setUp() {
        tag1 = Tag.builder()
                .id(1L)
                .tagName(HASHTAG_1)
                .tweetsQuantity(111L)
                .build();
        tag2 = Tag.builder()
                .id(2L)
                .tagName(HASHTAG_2)
                .tweetsQuantity(1L)
                .build();
    }

    @Test
    void getTagsByText() {
        List<String> expectedTags = Arrays.asList(MOCK_TEXT, MOCK_TEXT);
        when(tagRepository.getTagsByText(MOCK_TEXT)).thenReturn(expectedTags);
        List<String> actualTags = tagClientService.getTagsByText(MOCK_TEXT);
        assertEquals(2, actualTags.size());
        assertEquals(expectedTags, actualTags);
        verify(tagRepository, times(1)).getTagsByText(MOCK_TEXT);
    }

    @Test
    void parseHashtagsFromText() {
        when(tagRepository.findByTagName(HASHTAG_1)).thenReturn(Optional.of(tag1));
        when(tagRepository.findByTagName(HASHTAG_2)).thenReturn(Optional.empty());
        doNothing().when(tagRepository).updateTagQuantity(tag1.getId(), true);
        when(tweetTagRepository.save(any())).thenReturn(null);
        when(tagRepository.save(any())).thenReturn(tag2);
        tagClientService.parseHashtagsFromText(1L, "test text #hashtag1 #hashtag2");
        verify(tagRepository, times(1)).findByTagName(HASHTAG_1);
        verify(tagRepository, times(1)).findByTagName(HASHTAG_2);
        verify(tagRepository, times(1)).updateTagQuantity(tag1.getId(), true);
        verify(tagRepository, times(1)).save(any());
        verify(tweetTagRepository, times(2)).save(any());
    }

    @Test
    void parseHashtagsFromText_shouldProcessTextWithoutHashtag() {
        tagClientService.parseHashtagsFromText(1L, MOCK_TEXT);
        verify(tagRepository, times(0)).findByTagName(MOCK_TEXT);
    }

    @Test
    void deleteTagsByTweetId() {
        List<Long> tagsIds = Arrays.asList(1L, 2L);
        when(tweetTagRepository.getTagIdsByTweetId(1L)).thenReturn(tagsIds);
        when(tagRepository.getTagsByIds(tagsIds)).thenReturn(Arrays.asList(tag1, tag2));
        doNothing().when(tagRepository).delete(any());
        doNothing().when(tweetTagRepository).deleteTag(any());
        doNothing().when(tagRepository).updateTagQuantity(tag1.getId(), false);
        tagClientService.deleteTagsByTweetId(1L);
        verify(tagRepository, times(1)).updateTagQuantity(tag1.getId(), false);
        verify(tagRepository, times(1)).delete(tag2);
        verify(tweetTagRepository, times(1)).deleteTag(tag2.getId());
    }
}