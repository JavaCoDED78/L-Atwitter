package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.feign.UserClient;
import com.gmail.javacoded78.model.Bookmark;
import com.gmail.javacoded78.model.Tweet;
import com.gmail.javacoded78.repository.BookmarkRepository;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.BookmarkProjection;
import com.gmail.javacoded78.util.TestConstants;
import com.gmail.javacoded78.util.TestUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import java.util.Optional;

import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_DELETED;
import static com.gmail.javacoded78.constants.ErrorMessage.TWEET_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_NOT_FOUND;
import static com.gmail.javacoded78.constants.ErrorMessage.USER_PROFILE_BLOCKED;
import static com.gmail.javacoded78.service.TweetServiceTestHelper.createMockBookmarkProjectionList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class BookmarkServiceImplTest {

    private final BookmarkServiceImpl bookmarkService;

    @MockBean
    private final BookmarkRepository bookmarkRepository;

    @MockBean
    private final TweetRepository tweetRepository;

    @MockBean
    private final UserClient userClient;

    private static final PageRequest pageable = PageRequest.of(0, 20);
    private static Tweet tweet;

    @BeforeEach
    public void setUp() {
        TestUtil.mockAuthenticatedUserId();
        tweet = Tweet.builder()
                .deleted(false)
                .authorId(TestConstants.USER_ID)
                .build();
    }

    @Test
    void getUserBookmarks() {
        Page<BookmarkProjection> bookmark = new PageImpl<>(createMockBookmarkProjectionList(), pageable, 20);
        when(bookmarkRepository.getUserBookmarks(TestConstants.USER_ID, pageable)).thenReturn(bookmark);
        assertEquals(bookmark, bookmarkService.getUserBookmarks(pageable));
        verify(bookmarkRepository, times(1)).getUserBookmarks(TestConstants.USER_ID, pageable);
    }

    @Test
    void processUserBookmarks_ShouldDeleteBookmark() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(bookmarkRepository.getUserBookmark(TestConstants.USER_ID, TestConstants.TWEET_ID)).thenReturn(new Bookmark());
        assertFalse(bookmarkService.processUserBookmarks(TestConstants.TWEET_ID));
        verify(bookmarkRepository, times(1)).getUserBookmark(TestConstants.USER_ID, TestConstants.TWEET_ID);
        verify(bookmarkRepository, times(1)).delete(new Bookmark());
    }

    @Test
    void processUserBookmarks_ShouldCreateBookmark() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(bookmarkRepository.getUserBookmark(TestConstants.USER_ID, TestConstants.TWEET_ID)).thenReturn(null);
        assertTrue(bookmarkService.processUserBookmarks(TestConstants.TWEET_ID));
        verify(bookmarkRepository, times(1)).getUserBookmark(TestConstants.USER_ID, TestConstants.TWEET_ID);
        verify(bookmarkRepository, times(1)).save(Bookmark.builder()
                .userId(TestConstants.USER_ID)
                .tweetId(TestConstants.TWEET_ID)
                .build());
    }

    @Test
    void processUserBookmarks_ShouldTweetNotFound() {
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> bookmarkService.processUserBookmarks(TestConstants.TWEET_ID));
        assertEquals(TWEET_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void processUserBookmarks_ShouldTweetDeleted() {
        tweet.setDeleted(true);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> bookmarkService.processUserBookmarks(TestConstants.TWEET_ID));
        assertEquals(TWEET_DELETED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void processUserBookmarks_ShouldUserNotFound() {
        tweet.setAuthorId(1L);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> bookmarkService.processUserBookmarks(TestConstants.TWEET_ID));
        assertEquals(USER_NOT_FOUND, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void processUserBookmarks_ShouldUserProfileBlocked() {
        tweet.setAuthorId(1L);
        when(tweetRepository.findById(TestConstants.TWEET_ID)).thenReturn(Optional.of(tweet));
        when(userClient.isUserHavePrivateProfile(1L)).thenReturn(false);
        when(userClient.isMyProfileBlockedByUser(1L)).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,
                () -> bookmarkService.processUserBookmarks(TestConstants.TWEET_ID));
        assertEquals(USER_PROFILE_BLOCKED, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void getIsTweetBookmarked() {
        when(bookmarkRepository.isUserBookmarkedTweet(TestConstants.USER_ID, TestConstants.TWEET_ID)).thenReturn(true);
        assertTrue(bookmarkService.getIsTweetBookmarked(TestConstants.TWEET_ID));
        verify(bookmarkRepository, times(1)).isUserBookmarkedTweet(TestConstants.USER_ID, TestConstants.TWEET_ID);
    }
}
