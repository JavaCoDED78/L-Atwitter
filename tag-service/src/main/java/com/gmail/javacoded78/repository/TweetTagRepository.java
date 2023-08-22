package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.TweetTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetTagRepository extends JpaRepository<TweetTag, Long> {

    @Query("SELECT tt.tagId FROM TweetTag tt WHERE tt.tweetId = :tweetId")
    List<Long> getTagIdsByTweetId(@Param("tweetId") Long tweetId);

    @Query("SELECT tt.tweetId FROM TweetTag tt WHERE tt.tagId = :tagId")
    List<Long> getTweetIdsByTagId(@Param("tagId") Long tagId);

    @Modifying
    @Query("DELETE FROM TweetTag tt WHERE tt.tagId = :tagId")
    void deleteTag(@Param("tagId") Long tagId);
}
