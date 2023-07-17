package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Bookmark;
import com.gmail.javacoded78.latwitter.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query(value = "SELECT * FROM bookmarks WHERE users_id = ?1 ORDER BY bookmarks.bookmark_date DESC", nativeQuery = true)
    Page<Bookmark> findByUser(User user, Pageable pageable);
}
