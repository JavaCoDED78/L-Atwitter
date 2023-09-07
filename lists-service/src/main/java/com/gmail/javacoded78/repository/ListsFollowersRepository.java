package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.ListsFollowers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListsFollowersRepository extends JpaRepository<ListsFollowers, Long> {

    @Query("""
            SELECT CASE WHEN count(lf) > 0
                THEN true
                ELSE false END
            FROM ListsFollowers lf
            WHERE lf.followerId = :userId
            AND lf.listId = :listId
            """)
    boolean isListFollowed(@Param("userId") Long userId, @Param("listId") Long listId);

    @Query("""
            SELECT lf
            FROM ListsFollowers lf
            WHERE lf.listId = :listId
            AND lf.followerId = :userId
            """)
    ListsFollowers getListFollower(@Param("listId") Long listId, @Param("userId") Long userId);

    @Query("""
            SELECT lf.followerId
            FROM ListsFollowers lf
            WHERE lf.listId = :listId
            """)
    List<Long> getFollowersIds(@Param("listId") Long listId);

    @Query("""
            SELECT COUNT(lf)
            FROM ListsFollowers lf
            WHERE lf.listId = :listId
            """)
    Long getFollowersSize(@Param("listId") Long listId);
}
