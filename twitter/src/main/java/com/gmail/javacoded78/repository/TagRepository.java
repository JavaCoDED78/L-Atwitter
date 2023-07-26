package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.Tag;
import com.gmail.javacoded78.repository.projection.tag.TagProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Page<TagProjection> findByOrderByTweetsQuantityDesc(Pageable pageable);

    List<TagProjection> findTop5ByOrderByTweetsQuantityDesc();

    Tag findByTagName(String tagName);

    List<Tag> findByTweets_Id(Long id);
}
