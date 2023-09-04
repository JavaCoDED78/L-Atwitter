package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.enums.NotificationType;
import com.gmail.javacoded78.model.Notification;
import com.gmail.javacoded78.repository.projection.NotificationInfoProjection;
import com.gmail.javacoded78.repository.projection.NotificationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("""
            SELECT CASE WHEN count(n) > 0
                THEN true
                ELSE false END
            FROM Notification n
            WHERE n.notifiedUserId = :userId
            AND n.listId = :listId
            AND n.userId = :authUserId
            AND n.notificationType = :notificationType
            """)
    boolean isListNotificationExists(@Param("userId") Long userId,
                                     @Param("listId") Long listId,
                                     @Param("authUserId") Long authUserId,
                                     @Param("notificationType") NotificationType type);

    @Query("""
            SELECT CASE WHEN count(n) > 0
                THEN true
                ELSE false END
            FROM Notification n
            WHERE n.notifiedUserId = :userId
            AND n.userToFollowId = :userToFollowId
            AND n.userId = :authUserId
            AND n.notificationType = :notificationType
            """)
    boolean isUserNotificationExists(@Param("userId") Long userId,
                                     @Param("userToFollowId") Long userToFollowId,
                                     @Param("authUserId") Long authUserId,
                                     @Param("notificationType") NotificationType type);

    @Query("""
            SELECT CASE WHEN count(n) > 0
                THEN true
                ELSE false END
            FROM Notification n
            WHERE n.notifiedUserId = :userId
            AND n.tweetId = :tweetId
            AND n.userId = :authUserId
            AND n.notificationType = :notificationType
            """)
    boolean isTweetNotificationExists(@Param("userId") Long userId,
                                      @Param("tweetId") Long tweetId,
                                      @Param("authUserId") Long authUserId,
                                      @Param("notificationType") NotificationType type);

    @Query("""
            SELECT n
            FROM Notification n
            WHERE n.notifiedUserId = :userId
            AND n.notificationType NOT IN ('TWEET', 'MENTION')
            ORDER BY n.date DESC
            """)
    Page<NotificationProjection> getNotificationsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT n.tweetId
            FROM Notification n
            WHERE n.notifiedUserId = :userId
            AND n.notificationType = 'MENTION'
            ORDER BY n.date DESC
            """)
    Page<Long> getTweetNotificationMentionIds(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT n.tweetId
            FROM Notification n
            WHERE n.userId IN :userIds
            AND n.notificationType = 'TWEET'
            AND n.notifiedUserId = :userId
            """)
    Page<Long> getTweetIdsByNotificationType(@Param("userIds") List<Long> userIds,
                                             @Param("userId") Long userId,
                                             Pageable pageable);

    @Query("""
            SELECT n
            FROM Notification n
            WHERE n.notifiedUserId = :userId
            AND n.id = :notificationId
            """)
    Optional<NotificationInfoProjection> getUserNotificationById(@Param("userId") Long userId,
                                                                 @Param("notificationId") Long notificationId);
}
