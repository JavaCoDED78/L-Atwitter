package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.LikeTweet;
import com.gmail.javacoded78.repository.projection.LikeTweetProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeTweetRepository extends JpaRepository<LikeTweet, Long> {

    @Query("""
            SELECT lt FROM LikeTweet lt
            WHERE lt.userId = :userId
            ORDER BY lt.likeTweetDate DESC
            """)
    Page<LikeTweetProjection> getUserLikedTweets(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT CASE WHEN count(lt) > 0 THEN true
                ELSE false END
            FROM LikeTweet lt
            WHERE lt.userId = :userId
            AND lt.tweetId = :tweetId
            """)
    boolean isUserLikedTweet(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Query("""
            SELECT COUNT(lt)
            FROM LikeTweet lt
            WHERE lt.tweetId = :tweetId
            """)
    Long getLikedTweetsSize(@Param("tweetId") Long tweetId);

    @Query("""
            SELECT lt.userId
            FROM LikeTweet lt
            WHERE lt.tweetId = :tweetId
            """)
    List<Long> getLikedUserIds(@Param("tweetId") Long tweetId);

    @Query("""
            SELECT lt FROM LikeTweet lt
            WHERE lt.userId = :userId
            AND lt.tweetId = :tweetId
            """)
    LikeTweet getLikedTweet(@Param("userId") Long userId, @Param("tweetId") Long tweetId);
}
