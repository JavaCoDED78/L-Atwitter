package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.ListsMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListsMembersRepository extends JpaRepository<ListsMembers, Long> {

    @Query("""
            SELECT lm
            FROM ListsMembers lm
            WHERE lm.listId = :listId
            AND lm.memberId = :userId
            """)
    ListsMembers getListMember(@Param("listId") Long listId, @Param("userId") Long userId);

    @Query("""
            SELECT lm.memberId
            FROM ListsMembers lm
            WHERE lm.listId = :listId
            """)
    List<Long> getMembersIds(@Param("listId") Long listId);

    @Query("""
            SELECT COUNT(lm)
            FROM ListsMembers lm
            WHERE lm.listId = :listId
            """)
    Long getMembersSize(@Param("listId") Long listId);
}
