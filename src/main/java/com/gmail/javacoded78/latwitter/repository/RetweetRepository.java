package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Retweet;
import com.gmail.javacoded78.latwitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    List<Retweet> findByUserOrderByRetweetDateDesc(User user);
}
