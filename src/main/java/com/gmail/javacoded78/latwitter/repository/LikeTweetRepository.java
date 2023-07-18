package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.LikeTweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeTweetRepository extends JpaRepository<LikeTweet, Long> {

    @Query("SELECT likeTweet FROM LikeTweet likeTweet WHERE likeTweet.user.id = :userId ORDER BY likeTweet.likeTweetDate DESC")
    Page<LikeTweet> findByUserId(Long userId, Pageable pageable);
}
