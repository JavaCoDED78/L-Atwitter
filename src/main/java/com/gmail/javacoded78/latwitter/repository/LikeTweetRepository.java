package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.LikeTweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeTweetRepository extends JpaRepository<LikeTweet, Long> {

    Page<LikeTweet> findByUser_IdOrderByLikeTweetDateDesc(Long id, Pageable pageable);
}
