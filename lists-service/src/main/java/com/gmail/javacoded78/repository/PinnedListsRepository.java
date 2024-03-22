package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.PinnedLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PinnedListsRepository extends JpaRepository<PinnedLists, Long> {

    @Modifying
    @Query(value = "DELETE FROM pinned_lists WHERE list_id = ?1", nativeQuery = true)
    void deletePinnedList(@Param("listId") Long listId);

    @Query("""
            SELECT pl
            FROM PinnedLists pl
            WHERE pl.list.id = :listId
            AND pl.pinnedUserId = :userId
            """)
    PinnedLists getPinnedByUserIdAndListId(@Param("listId") Long listId, @Param("userId") Long userId);

    @Query("""
            SELECT CASE WHEN count(pl) > 0
                THEN true
                ELSE false END
            FROM PinnedLists pl
            WHERE pl.list.id = :pinnedListId
            AND pl.pinnedUserId = :pinnedUserId
            """)
    boolean isListPinned(@Param("pinnedListId") Long pinnedListId, @Param("pinnedUserId") Long pinnedUserId);

    @Modifying
    @Query(value = "DELETE FROM pinned_lists_demo WHERE list_id = ?1 AND pinned_user_id = ?2", nativeQuery = true)
    void removePinnedList(@Param("listId") Long listId, @Param("userId") Long userId);
}
