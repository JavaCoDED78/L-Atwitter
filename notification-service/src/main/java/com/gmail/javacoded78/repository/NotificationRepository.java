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

    @Query("SELECT CASE WHEN count(notification) > 0 THEN true ELSE false END FROM Notification notification " +
            "WHERE notification.notifiedUserId = :userId " +
            "AND notification.listId = :listId " +
            "AND notification.notificationType = :notificationType")
    boolean isListNotificationExists(@Param("userId") Long userId,
                                     @Param("listId") Long listId,
                                     @Param("notificationType") NotificationType type);

    @Query("SELECT CASE WHEN count(notification) > 0 THEN true ELSE false END FROM Notification notification " +
            "WHERE notification.notifiedUserId = :userId " +
            "AND notification.userToFollowId = :listId " +
            "AND notification.notificationType = :notificationType")
    boolean isUserNotificationExists(@Param("userId") Long userId,
                                     @Param("userToFollowId") Long userToFollowId,
                                     @Param("notificationType") NotificationType type);

    @Query("SELECT CASE WHEN count(notification) > 0 THEN true ELSE false END FROM Notification notification " +
            "WHERE notification.notifiedUserId = :userId " +
            "AND notification.tweetId = :tweetId " +
            "AND notification.notificationType = :notificationType")
    boolean isTweetNotificationExists(@Param("userId") Long userId,
                                      @Param("tweetId") Long tweetId,
                                      @Param("notificationType") NotificationType type);

    @Query("SELECT notification FROM Notification notification " +
            "WHERE notification.notifiedUserId = :userId " +
            "AND notification.notificationType != 'TWEET' " +
            "ORDER BY notification.date DESC")
    Page<NotificationProjection> getNotificationsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT notification.tweetId FROM Notification notification " +
            "WHERE notification.userId IN :userIds " +
            "AND notification.notificationType = 'TWEET' " +
            "AND notification.notifiedUserId = :userId")
    List<Long> getTweetIdsByNotificationType(@Param("userIds") List<Long> userIds, @Param("userId") Long userId);

    @Query("SELECT notification FROM Notification notification " +
            "WHERE notification.userId = :userId " +
            "AND notification.id = :notificationId")
    Optional<NotificationInfoProjection> getUserNotificationById(@Param("userId") Long userId,
                                                                 @Param("notificationId") Long notificationId);
}
