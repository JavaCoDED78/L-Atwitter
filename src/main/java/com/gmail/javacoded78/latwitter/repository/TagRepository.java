package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Tag;
import com.gmail.javacoded78.latwitter.repository.projection.tag.TagProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<TagProjection> findByOrderByTweetsQuantityDesc();

    List<TagProjection> findTop5ByOrderByTweetsQuantityDesc();

    Tag findByTagName(String tagName);

    List<Tag> findByTweets_Id(Long id);
}
