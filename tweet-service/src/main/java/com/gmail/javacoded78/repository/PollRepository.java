package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

    @Query("""
            SELECT p FROM Poll p
            LEFT JOIN p.pollChoices pch
            WHERE p.id = :pollId
            AND pch.id = :pollChoiceId
            """)
    Optional<Poll> getPollByPollChoiceId(@Param("pollId") Long pollId, @Param("pollChoiceId") Long pollChoiceId);
}
