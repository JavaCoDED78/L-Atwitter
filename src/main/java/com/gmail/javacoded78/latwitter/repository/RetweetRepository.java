package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Retweet;
import com.gmail.javacoded78.latwitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    @Query(value = "SELECT * FROM retweets WHERE users_id = ?1 ORDER BY retweets.retweet_date DESC", nativeQuery = true)
    List<Retweet> findRetweetsByUserId(Long userId);
}
