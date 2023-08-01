package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.LikeTweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeTweetRepository extends JpaRepository<LikeTweet, Long> {

    @Query("SELECT CASE WHEN count(likeTweet) > 0 THEN true ELSE false END " +
            "FROM LikeTweet likeTweet " +
            "WHERE likeTweet.userId = :userId " +
            "AND likeTweet.tweetId = :tweetId")
    boolean isUserLikedTweet(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Query("SELECT COUNT(likeTweet) FROM LikeTweet likeTweet WHERE likeTweet.tweetId = :tweetId")
    Long getLikedTweetsSize(@Param("tweetId") Long tweetId);

    @Query("SELECT likeTweet.userId FROM LikeTweet likeTweet WHERE likeTweet.tweetId = :tweetId")
    Page<Long> getLikedUserIds(@Param("tweetId") Long tweetId, Pageable pageable);

    @Query("SELECT likeTweet FROM LikeTweet likeTweet " +
            "WHERE likeTweet.userId = :userId " +
            "AND likeTweet.tweetId = :tweetId")
    LikeTweet getLikedTweet(@Param("userId") Long userId, @Param("tweetId") Long tweetId);
}
