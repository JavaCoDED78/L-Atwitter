package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Retweet;
import com.gmail.javacoded78.latwitter.repository.projection.RetweetProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    @Query("SELECT retweet FROM Retweet retweet WHERE retweet.user.id = :userId ORDER BY retweet.retweetDate DESC")
    List<RetweetProjection> findRetweetsByUserId(Long userId);

    @Query("SELECT COUNT(retweet) FROM Tweet tweet LEFT JOIN tweet.retweets retweet WHERE tweet.id = :tweetId")
    Integer getRetweetsCount(Long tweetId);
}
