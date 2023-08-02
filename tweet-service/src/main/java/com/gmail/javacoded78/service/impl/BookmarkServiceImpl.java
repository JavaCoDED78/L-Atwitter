package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.exception.ApiRequestException;
import com.gmail.javacoded78.model.Bookmark;
import com.gmail.javacoded78.repository.BookmarkRepository;
import com.gmail.javacoded78.repository.TweetRepository;
import com.gmail.javacoded78.repository.projection.BookmarkProjection;
import com.gmail.javacoded78.service.BookmarkService;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final TweetRepository tweetRepository;

    @Override
    public Page<BookmarkProjection> getUserBookmarks(Pageable pageable) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        return bookmarkRepository.getUserBookmarks(userId, pageable);
    }

    @Override
    public Boolean processUserBookmarks(Long tweetId) {
        Long userId = AuthUtil.getAuthenticatedUserId();
        tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiRequestException("Tweet not found", HttpStatus.NOT_FOUND));
        Bookmark bookmark = bookmarkRepository.getUserBookmark(userId, tweetId);

        if (bookmark != null) {
            bookmarkRepository.delete(bookmark);
            return false;
        } else {
            Bookmark newBookmark = new Bookmark(userId, tweetId);
            bookmarkRepository.save(newBookmark);
            return true;
        }
    }

    @Override
    public Boolean getIsTweetBookmarked(Long tweetId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return bookmarkRepository.isUserBookmarkedTweet(authUserId, tweetId);
    }
}
