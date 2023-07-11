package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
}
