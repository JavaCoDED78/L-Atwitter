package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.TweetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetImageRepository extends JpaRepository<TweetImage, Long> {
}
