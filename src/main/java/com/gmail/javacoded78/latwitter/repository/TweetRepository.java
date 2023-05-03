package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findAllByOrderByDateTimeDesc();

    List<Tweet> findAllByUserOrderByDateTimeDesc(User user);
}
