package com.gmail.javacoded78.service;

import com.gmail.javacoded78.repository.projection.BookmarkProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkService {

    Page<BookmarkProjection> getUserBookmarks(Pageable pageable);

    Boolean processUserBookmarks(Long tweetId);

    Boolean getIsTweetBookmarked(Long tweetId);
}
