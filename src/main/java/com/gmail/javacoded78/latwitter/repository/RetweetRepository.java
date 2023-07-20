package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Retweet;
import com.gmail.javacoded78.latwitter.repository.projection.tweet.RetweetProjection;
import com.gmail.javacoded78.latwitter.repository.projection.tweet.RetweetsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    @Query("SELECT r AS retweet FROM Retweet r WHERE r.user.id = :userId ORDER BY r.retweetDate DESC")
    List<RetweetsProjection> findRetweetsByUserId(Long userId);

    @Query("SELECT COUNT(retweet) FROM Tweet tweet LEFT JOIN tweet.retweets retweet WHERE tweet.id = :tweetId")
    Integer getRetweetsCount(Long tweetId);
}
