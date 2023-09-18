package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.PollChoiceVoted;
import com.gmail.javacoded78.repository.projection.VotedUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollChoiceVotedRepository extends JpaRepository<PollChoiceVoted, Long> {

    @Query("""
        SELECT pch.votedUserId as id FROM PollChoiceVoted pch
        WHERE pch.pollChoiceId = :pollChoiceId
        """)
    List<VotedUserProjection> getVotedUserIds(@Param("pollChoiceId") Long pollChoiceId);

    @Query("""
            SELECT CASE WHEN count(pch) > 0 THEN true
                ELSE false END
            FROM PollChoiceVoted pch
            WHERE pch.votedUserId = :votedUserId
            AND pch.pollChoiceId = :pollChoiceId
            """)
    boolean ifUserVoted(@Param("votedUserId") Long votedUserId, @Param("pollChoiceId") Long pollChoiceId);
}
