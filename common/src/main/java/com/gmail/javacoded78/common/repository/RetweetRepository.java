package com.gmail.javacoded78.common.repository;

import com.gmail.javacoded78.common.models.Retweet;
import com.gmail.javacoded78.common.projection.RetweetsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    @Query("SELECT r AS retweet FROM Retweet r " +
            "LEFT JOIN r.user u " +
            "LEFT JOIN r.tweet t " +
            "WHERE u.id = :userId " +
            "AND t.deleted = false " +
            "ORDER BY r.retweetDate DESC")
    List<RetweetsProjection> findRetweetsByUserId(Long userId);

    @Query("SELECT CASE WHEN count(retweet) > 0 THEN true ELSE false END FROM Retweet retweet " +
            "WHERE retweet.user.id = :userId " +
            "AND retweet.tweet.id = :tweetId")
    boolean isTweetRetweeted(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Modifying
    @Query(value = "DELETE FROM retweets WHERE users_id = ?1 AND tweets_id = ?2", nativeQuery = true)
    void removeRetweetedTweet(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Modifying
    @Query(value = "INSERT INTO retweets (id, users_id, tweets_id) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void addRetweetedTweet(@Param("id") BigDecimal id, @Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Query(value = "SELECT nextval('retweets_seq')", nativeQuery = true)
    BigDecimal getNextVal();
}
