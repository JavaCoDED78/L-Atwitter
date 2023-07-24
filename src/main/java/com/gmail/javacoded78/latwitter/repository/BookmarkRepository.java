package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Bookmark;
import com.gmail.javacoded78.latwitter.repository.projection.BookmarkProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT bookmark FROM Bookmark bookmark " +
            "LEFT JOIN bookmark.user user " +
            "LEFT JOIN bookmark.tweet tweet " +
            "WHERE user.id = :userId " +
            "AND tweet.deleted = false " +
            "ORDER BY bookmark.bookmarkDate DESC")
    Page<BookmarkProjection> getUserBookmarks(Long userId, Pageable pageable);
}
