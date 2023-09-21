package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.service.BookmarkService;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class BookmarkMapperTest {

    private final BookmarkMapper bookmarkMapper;

    @MockBean
    private final BookmarkService bookmarkService;

    @Test
    void processUserBookmarks() {
        when(bookmarkService.processUserBookmarks(TestConstants.TWEET_ID)).thenReturn(true);
        assertTrue(bookmarkMapper.processUserBookmarks(TestConstants.TWEET_ID));
        verify(bookmarkService, times(1)).processUserBookmarks(TestConstants.TWEET_ID);
    }

    @Test
    void getIsTweetBookmarked() {
        when(bookmarkService.getIsTweetBookmarked(TestConstants.TWEET_ID)).thenReturn(true);
        assertTrue(bookmarkMapper.getIsTweetBookmarked(TestConstants.TWEET_ID));
        verify(bookmarkService, times(1)).getIsTweetBookmarked(TestConstants.TWEET_ID);
    }
}
