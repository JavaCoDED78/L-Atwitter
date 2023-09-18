package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.Bookmark;
import com.gmail.javacoded78.repository.projection.BookmarkProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("""
            SELECT CASE WHEN count(b) > 0 THEN true
                ELSE false END
            FROM Bookmark b
            WHERE b.userId = :userId
            AND b.tweetId = :tweetId
            """)
    boolean isUserBookmarkedTweet(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Query("""
            SELECT b FROM Bookmark b
            WHERE b.userId = :userId
            ORDER BY b.bookmarkDate DESC
            """)
    Page<BookmarkProjection> getUserBookmarks(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT b FROM Bookmark b
            WHERE b.userId = :userId
            AND b.tweetId = :tweetId
            """)
    Bookmark getUserBookmark(@Param("userId") Long userId, @Param("tweetId") Long tweetId);
}
