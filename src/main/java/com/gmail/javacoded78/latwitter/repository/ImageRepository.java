package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT image FROM Tweet tweet LEFT JOIN tweet.images image WHERE tweet.id = :tweetId")
    List<Image> findImagesByTweetId(Long tweetId);
}
