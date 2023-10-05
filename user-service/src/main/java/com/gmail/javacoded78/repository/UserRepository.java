package com.gmail.javacoded78.repository;


import com.gmail.javacoded78.model.User;
import com.gmail.javacoded78.repository.projection.AuthUserProjection;
import com.gmail.javacoded78.repository.projection.CommonUserProjection;
import com.gmail.javacoded78.repository.projection.ListMemberProjection;
import com.gmail.javacoded78.repository.projection.NotificationUserProjection;
import com.gmail.javacoded78.repository.projection.TaggedUserProjection;
import com.gmail.javacoded78.repository.projection.UserCommonProjection;
import com.gmail.javacoded78.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT u FROM User u
            WHERE u.email = :email
            """)
    <T> Optional<T> getUserByEmail(@Param("email") String email, Class<T> type);

    @Query("""
            SELECT u FROM User u
            WHERE u.id = :userId
            """)
    <T> Optional<T> getUserById(@Param("userId") Long userId, Class<T> type);

    @Query("""
            SELECT u FROM User u
            WHERE u.activationCode = :code
            """)
    Optional<UserCommonProjection> getCommonUserByActivationCode(@Param("code") String code);

    @Query("""
            SELECT u.activationCode FROM User u
            WHERE u.id = :userId
            """)
    String getActivationCode(@Param("userId") Long userId);

    @Query("""
            SELECT u FROM User u
            WHERE u.passwordResetCode = :code
            """)
    Optional<AuthUserProjection> getByPasswordResetCode(@Param("code") String code);

    @Query("""
            SELECT u.passwordResetCode FROM User u
            WHERE u.id = :userId
            """)
    String getPasswordResetCode(@Param("userId") Long userId);

    @Query("""
            SELECT u.password FROM User u
            WHERE u.id = :userId
            """)
    String getUserPasswordById(@Param("userId") Long userId);

    @Query("""
            SELECT u.id FROM User u
            WHERE UPPER(u.username) = UPPER(:username)
            """)
    Long getUserIdByUsername(@Param("username") String username);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.passwordResetCode = :passwordResetCode
            WHERE u.id = :userId
            """)
    void updatePasswordResetCode(@Param("passwordResetCode") String passwordResetCode, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.password = :password
            WHERE u.id = :userId
            """)
    void updatePassword(@Param("password") String password, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.active = true
            WHERE u.id = :userId
            """)
    void updateActiveUserProfile(@Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.activationCode = :activationCode
            WHERE u.id = :userId
            """)
    void updateActivationCode(@Param("activationCode") String activationCode, @Param("userId") Long userId);

    Page<UserProjection> findByActiveTrueAndIdNot(Long id, Pageable pageable);

    List<UserProjection> findTop5ByActiveTrue();

    @Query("""
            SELECT u FROM User u
            WHERE UPPER(u.fullName) LIKE UPPER(CONCAT('%',:username,'%'))
                AND u.active = true
                OR UPPER(u.username) LIKE UPPER(CONCAT('%',:username,'%'))
                AND u.active = true
                """)
    <T> Page<T> searchUsersByUsername(@Param("username") String name, Pageable pageable, Class<T> type);

    @Query("""
            SELECT u FROM User u
            WHERE UPPER(u.fullName) LIKE UPPER(CONCAT('%',:text,'%'))
                AND u.active = true
                OR UPPER(u.username) LIKE UPPER(CONCAT('%',:text,'%'))
                AND u.active = true
                """)
    List<CommonUserProjection> searchUserByText(@Param("text") String text);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.profileStarted = true
            WHERE u.id = :userId
            """)
    void updateProfileStarted(@Param("userId") Long userId);

    @Query("""
            SELECT CASE WHEN count(u) > 0 THEN true
                ELSE false END
            FROM User u
            WHERE u.id = :userId
            """)
    boolean isUserExist(@Param("userId") Long userId);

    @Query("""
            SELECT u.privateProfile FROM User u
            WHERE u.id = :userId
            """)
    boolean getUserPrivateProfile(@Param("userId") Long userId);

    @Query("""
            SELECT CASE WHEN count(u) > 0 THEN true
                ELSE false END
            FROM User u
            LEFT JOIN u.following f
            WHERE u.id = :userId
                AND u.privateProfile = false
                OR u.id = :userId
                AND u.privateProfile = true
                AND f.id = :authUserId
            """)
    boolean isUserHavePrivateProfile(@Param("userId") Long userId, @Param("authUserId") Long authUserId);

    @Query("""
            SELECT CASE WHEN count(fr) > 0 THEN true
                ELSE false END
            FROM User u
            LEFT JOIN u.followerRequests fr
            WHERE u.id = :userId
                AND fr.id = :authUserId
            """)
    boolean isMyProfileWaitingForApprove(@Param("userId") Long userId, @Param("authUserId") Long authUserId);

    @Query("""
            SELECT CASE WHEN count(subscriber) > 0 THEN true
                ELSE false END
            FROM User u
            LEFT JOIN u.subscribers subscriber
            WHERE u.id = :userId
                AND subscriber.id = :subscriberUserId
            """)
    boolean isMyProfileSubscribed(@Param("userId") Long userId, @Param("subscriberUserId") Long subscriberUserId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.notificationsCount = u.notificationsCount + 1
            WHERE u.id = :userId
            """)
    void increaseNotificationsCount(@Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.mentionsCount = u.mentionsCount + 1
            WHERE u.id = :userId
            """)
    void increaseMentionsCount(@Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.likeCount = CASE WHEN :increaseCount = true THEN (u.likeCount + 1)
                ELSE (u.likeCount - 1) END
            WHERE u.id = :userId
            """)
    void updateLikeCount(@Param("increaseCount") boolean increaseCount, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.tweetCount = CASE WHEN :increaseCount = true THEN (u.tweetCount + 1)
                ELSE (u.tweetCount - 1) END
            WHERE u.id = :userId
            """)
    void updateTweetCount(@Param("increaseCount") boolean increaseCount, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.mediaTweetCount = CASE WHEN :increaseCount = true THEN (u.mediaTweetCount + 1)
                ELSE (u.mediaTweetCount - 1) END
            WHERE u.id = :userId
            """)
    void updateMediaTweetCount(@Param("increaseCount") boolean increaseCount, @Param("userId") Long userId);

    @Query("""
            SELECT u FROM User u
            WHERE u.id IN :userIds
            """)
    <T> List<T> getUsersByIds(@Param("userIds") List<Long> userIds, Class<T> type);

    @Query("""
            SELECT u FROM User u
            WHERE UPPER(u.fullName) LIKE UPPER(CONCAT('%',:username,'%'))
                AND u.active = true
                OR UPPER(u.username) LIKE UPPER(CONCAT('%',:username,'%'))
                AND u.active = true
            """)
    List<ListMemberProjection> searchListMembersByUsername(@Param("username") String username);

    @Query("""
            SELECT u FROM User u
            WHERE u.id IN :userIds
            """)
    Page<UserProjection> getUsersByIds(@Param("userIds") List<Long> userIds, Pageable pageable);

    @Query("""
            SELECT u.pinnedTweetId FROM User u
            WHERE u.id = :userId
            """)
    Long getPinnedTweetId(@Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.pinnedTweetId = :tweetId
            WHERE u.id = :userId
            """)
    void updatePinnedTweetId(@Param("tweetId") Long tweetId, @Param("userId") Long userId);

    @Query("""
            SELECT u.id FROM User u
            LEFT JOIN u.following f
            WHERE u.id IN :userIds
                AND (u.privateProfile = false
                    OR (u.privateProfile = true
                        AND (f.id = :userId
                            OR u.id = :userId
                        )
                    )
                    AND u.active = true
                )
            """)
    List<Long> getValidUserIdsByIds(@Param("userIds") List<Long> userIds, @Param("userId") Long userId);

    @Query("""
            SELECT u.id FROM User u
            LEFT JOIN u.following following
            WHERE (UPPER(u.fullName) LIKE UPPER(CONCAT('%',:username,'%'))
                AND (u.privateProfile = false
                    OR (u.privateProfile = true AND following.id IN :userIds)
                    AND u.active = true)
                    )
                OR (UPPER(u.username) LIKE UPPER(CONCAT('%',:username,'%'))
                AND (u.privateProfile = false
                    OR (u.privateProfile = true
                        AND following.id IN :userIds)
                    AND u.active = true)
                   )
            """)
    List<Long> getValidUserIdsByName(@Param("username") String username, @Param("userIds") List<Long> userIds);

    @Query("""
            SELECT CASE WHEN count(u) > 0 THEN true
                ELSE false END
            FROM User u
            WHERE u.id = :userId
            """)
    boolean isUserExists(@Param("userId") Long userId);

    @Query("""
            SELECT u.id FROM User u
            LEFT JOIN u.userBlockedList bu
            WHERE u.id IN :userIds
                AND bu.id = :authUserId
            """)
    List<Long> getUserIdsWhoBlockedMyProfile(@Param("userIds") List<Long> userIds, @Param("authUserId") Long authUserId);

    @Query("""
            SELECT s.id FROM User u
            JOIN u.subscribers s
            WHERE u.id = :userId
            """)
    List<Long> getSubscribersByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT u FROM User u
            LEFT JOIN u.subscribers s
            WHERE s.id = :userId
            """)
    List<NotificationUserProjection> getUsersWhichUserSubscribed(@Param("userId") Long userId);

    @Query("""
            SELECT u.id FROM User u
            LEFT JOIN u.subscribers s
            WHERE s.id = :userId
            """)
    List<Long> getUserIdsWhichUserSubscribed(@Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.notificationsCount = 0
            WHERE u.id = :userId
            """)
    void resetNotificationCount(@Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.mentionsCount = 0
            WHERE u.id = :userId
            """)
    void resetMentionCount(@Param("userId") Long userId);

    @Query("""
            SELECT CASE WHEN count(u) > 0 THEN true
                ELSE false END
            FROM User u
            LEFT JOIN u.subscribers s
            WHERE u.id = :userId
                AND s.id = :authUserId
            """)
    boolean isUserSubscribed(@Param("userId") Long userId, @Param("authUserId") Long authUserId);

    @Modifying
    @Query(value = """
                    INSERT INTO subscribers (subscriber_id, user_id)
                    VALUES (?1, ?2)
                    """, nativeQuery = true)
    void subscribe(@Param("authUserId") Long authUserId, @Param("userId") Long userId);

    @Modifying
    @Query(value = """
                    DELETE FROM subscribers
                    WHERE subscriber_id = ?1
                        AND user_id = ?2
                    """, nativeQuery = true)
    void unsubscribe(@Param("authUserId") Long authUserId, @Param("userId") Long userId);

    @Query("""
            SELECT u FROM User u
            WHERE u.id IN :userIds
            """)
    List<TaggedUserProjection> getTaggedImageUsers(@Param("userIds") List<Long> userIds);
}
