package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.model.Lists;
import com.gmail.javacoded78.repository.projection.BaseListProjection;
import com.gmail.javacoded78.repository.projection.ListProjection;
import com.gmail.javacoded78.repository.projection.ListUserProjection;
import com.gmail.javacoded78.repository.projection.PinnedListProjection;
import com.gmail.javacoded78.repository.projection.SimpleListProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListsRepository extends JpaRepository<Lists, Long> {
    String COMMON_LISTS_EXPRESSION = "list.id as id, list.name as name, list.description as description, " +
            "list.altWallpaper as altWallpaper, list.wallpaper as wallpaper, list.listOwnerId as listOwnerId ";
    String LISTS_EXPRESSION = COMMON_LISTS_EXPRESSION + ", list.isPrivate as isPrivate ";

    @Query("""
            SELECT l
            FROM Lists l
            WHERE l.id = :listId
            """)
    <T> T getListById(@Param("listId") Long listId, Class<T> type);

    @Query("""
            SELECT l
            FROM Lists l
            WHERE l.listOwnerId IN :listOwnerIds
            """)
    List<ListProjection> getAllTweetLists(@Param("listOwnerIds") List<Long> listOwnerIds);

    @Query("""
            SELECT DISTINCT l.listOwnerId
            FROM Lists l
            """)
    List<Long> getListOwnerIds();

    @Query("SELECT " + COMMON_LISTS_EXPRESSION +
            """
            FROM Lists list
            WHERE list.id IN (
                SELECT listsMembers.listId FROM ListsMembers listsMembers
                WHERE listsMembers.memberId = :userId)
            """)
    List<ListProjection> getTweetListsByIds(@Param("userId") Long userId);

    @Query("SELECT " + LISTS_EXPRESSION +
           """
            FROM Lists list
            WHERE list.listOwnerId = :userId
            OR list.id IN (
                SELECT listFollower.listId FROM ListsFollowers listFollower
                WHERE listFollower.followerId = :userId)
            """)
    List<ListUserProjection> getUserTweetLists(@Param("userId") Long userId);

    @Query("""
            SELECT l FROM Lists l
            LEFT JOIN l.pinnedLists pl
            WHERE pl.pinnedUserId = :userId
            ORDER BY pl.pinnedDate DESC
            """)
    List<PinnedListProjection> getUserPinnedLists(@Param("userId") Long userId);

    @Query("SELECT " + LISTS_EXPRESSION +
            """
            FROM Lists list
            WHERE list.id = :listId AND list.id IN (
               SELECT listFollower.listId FROM ListsFollowers listFollower
               WHERE listFollower.followerId = :userId)
            OR list.id = :listId AND list.isPrivate = false
            OR list.id = :listId AND list.listOwnerId = :userId
            """)
    <T> Optional<T> getListById(@Param("listId") Long listId, @Param("userId") Long userId, Class<T> type);

    @Query("""
            SELECT l
            FROM Lists l
            WHERE l.listOwnerId = :ownerId
            AND l.isPrivate = false
            """)
    List<ListProjection> getUserTweetListsById(@Param("ownerId") Long ownerId);

    @Query("""
            SELECT CASE WHEN count(l) > 0
                THEN true
                ELSE false END
            FROM Lists l
            WHERE l.id = :listId
            AND l.isPrivate = false
            """)
    boolean findByIdAndIsPrivateFalse(@Param("listId") Long listId);

    @Query("""
            SELECT l FROM Lists l
            WHERE l.id = :listId
            AND l.listOwnerId = :listOwnerId
            OR l.id = :listId
            AND l.id IN (
               SELECT lf.listId FROM ListsFollowers lf
               WHERE lf.followerId = :listOwnerId)
            """)
    Optional<Lists> getListWhereUserConsist(@Param("listId") Long listId, @Param("listOwnerId") Long listOwnerId);

    @Query("""
            SELECT l
            FROM Lists l
            WHERE l.listOwnerId = :ownerId
            """)
    List<SimpleListProjection> getUserOwnerLists(@Param("ownerId") Long ownerId);

    @Query("""
            SELECT CASE WHEN count(l) > 0
                THEN true
                ELSE false END
            FROM Lists l
            WHERE l.id = :listId
            AND l.listOwnerId = :authUserId
            AND l.id IN (
               SELECT lm.listId FROM ListsMembers lm
               WHERE lm.memberId = :memberId)
            """)
    boolean isListIncludeUser(@Param("listId") Long listId,
                              @Param("authUserId") Long authUserId,
                              @Param("memberId") Long memberId);

    @Query("""
            SELECT CASE WHEN count(l) > 0
                THEN true
                ELSE false END
            FROM Lists l
            WHERE l.id = :listId
            AND l.listOwnerId = :listOwnerId
            OR l.id = :listId AND l.id IN (
               SELECT lf.listId FROM ListsFollowers lf
               WHERE lf.followerId = :listOwnerId)
            """)
    boolean isListExist(@Param("listId") Long listId, @Param("listOwnerId") Long listOwnerId);

    @Query("""
            SELECT CASE WHEN count(l) > 0
                THEN true
                ELSE false END
            FROM Lists l
            WHERE l.id = :listId
            AND l.listOwnerId = :authUserId
            OR l.id = :listId
                AND l.isPrivate = false
            OR l.id = :listId
                AND l.isPrivate = true AND l.id IN (
                   SELECT lf.listId FROM ListsFollowers lf
                   WHERE lf.followerId = :authUserId)
            """)
    boolean isListNotPrivate(@Param("listId") Long listId, @Param("authUserId") Long authUserId);

    @Query("""
            SELECT l FROM Lists l
            WHERE l.id = :listId
                AND l.isPrivate = false
            OR l.id = :listId
                AND l.listOwnerId = :authUserId
            """)
    Optional<BaseListProjection> getListDetails(@Param("listId") Long listId, @Param("authUserId") Long authUserId);

    @Query("""
            SELECT l.isPrivate FROM Lists l
            WHERE l.id = :listId
            AND l.listOwnerId <> :authUserId
            """)
    boolean isListPrivate(@Param("listId") Long listId, @Param("authUserId") Long authUserId);
}
