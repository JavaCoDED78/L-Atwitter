package com.gmail.javacoded78.service.impl;

import com.gmail.javacoded78.model.Bookmark;
import com.gmail.javacoded78.repository.BookmarkRepository;
import com.gmail.javacoded78.repository.projection.BookmarkProjection;
import com.gmail.javacoded78.service.BookmarkService;
import com.gmail.javacoded78.service.util.TweetValidationHelper;
import com.gmail.javacoded78.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final TweetValidationHelper tweetValidationHelper;

    @Override
    public Page<BookmarkProjection> getUserBookmarks(Pageable pageable) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return bookmarkRepository.getUserBookmarks(authUserId, pageable);
    }

    @Override
    @Transactional
    public Boolean processUserBookmarks(Long tweetId) {
        tweetValidationHelper.checkValidTweet(tweetId);
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        Bookmark bookmark = bookmarkRepository.getUserBookmark(authUserId, tweetId);

        if (bookmark != null) {
            bookmarkRepository.delete(bookmark);
            return false;
        } else {
            Bookmark newBookmark = Bookmark.builder()
                    .userId(authUserId)
                    .tweetId(tweetId)
                    .build();
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
