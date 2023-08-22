package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Page<Tag> findByOrderByTweetsQuantityDesc(Pageable pageable);

    List<Tag> findTop5ByOrderByTweetsQuantityDesc();

    Optional<Tag> findByTagName(String tagName);

    @Query("SELECT t.tagName FROM Tag t WHERE UPPER(t.tagName) LIKE UPPER(CONCAT('%',:text,'%'))")
    List<String> getTagsByText(@Param("text") String text);

    @Query("SELECT t FROM Tag t WHERE t.id IN :tagIds")
    List<Tag> getTagsByIds(@Param("tagIds") List<Long> tagIds);

    @Modifying
    @Query("""
            UPDATE Tag t SET t.tweetsQuantity = CASE
                WHEN :increaseCount = true THEN (t.tweetsQuantity + 1)
                ELSE (t.tweetsQuantity - 1) END
            WHERE t.id = :tagId
            """)
    void updateTagQuantity(@Param("tagId") Long tagId, @Param("increaseCount") boolean increaseCount);
}
