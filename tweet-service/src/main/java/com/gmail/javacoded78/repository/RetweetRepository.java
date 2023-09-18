package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.Retweet;
import com.gmail.javacoded78.repository.projection.RetweetProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    @Query("""
            SELECT rt FROM Retweet rt
            WHERE rt.userId = :userId
            ORDER BY rt.retweetDate DESC
            """)
    List<RetweetProjection> getRetweetsByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT CASE WHEN count(rt) > 0 THEN true
                ELSE false END
            FROM Retweet rt
            WHERE rt.userId = :userId
            AND rt.tweetId = :tweetId
            """)
    boolean isUserRetweetedTweet(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Query("""
            SELECT COUNT(rt) FROM Retweet rt
            WHERE rt.tweetId = :tweetId
            """)
    Long getRetweetSize(@Param("tweetId") Long tweetId);

    @Query("""
            SELECT rt.userId FROM Retweet rt
            WHERE rt.tweetId = :tweetId
            """)
    List<Long> getRetweetedUserIds(@Param("tweetId") Long tweetId);

    @Query("""
            SELECT rt FROM Retweet rt
            WHERE rt.userId = :userId
            AND rt.tweetId = :tweetId
            """)
    Retweet isTweetRetweeted(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Query("""
            SELECT rt.userId FROM Retweet rt
            WHERE rt.tweetId = :tweetId
            """)
    List<Long> getRetweetsUserIds(@Param("tweetId") Long tweetId);
}
