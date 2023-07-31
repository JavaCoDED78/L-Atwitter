package com.gmail.javacoded78.common.repository;

import com.gmail.javacoded78.common.enums.NotificationType;
import com.gmail.javacoded78.common.models.Notification;
import com.gmail.javacoded78.common.projection.NotificationInfoProjection;
import com.gmail.javacoded78.common.projection.NotificationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT notification FROM Notification notification " +
            "WHERE notification.notifiedUser.id = :userId " +
            "AND notification.notificationType != 'TWEET' " +
            "ORDER BY notification.date DESC")
    Page<NotificationProjection> getNotificationsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT CASE WHEN count(notification) > 0 THEN true ELSE false END FROM Notification notification " +
            "WHERE notification.notifiedUser.id = :userId " +
            "AND notification.list.id = :listId " +
            "AND notification.notificationType = :notificationType")
    boolean isNotificationExists(@Param("userId") Long userId,
                                 @Param("listId") Long listId,
                                 @Param("notificationType") NotificationType type);

    @Query("SELECT n.id AS id, n.date AS date, n.notificationType AS notificationType, n.user AS user, n.tweet AS tweet " +
            "FROM User u " +
            "LEFT JOIN u.notifications n " +
            "WHERE u.id = :userId " +
            "AND n.id = :notificationId")
    Optional<NotificationInfoProjection> getUserNotificationById(@Param("userId") Long userId,
                                                                 @Param("notificationId") Long notificationId);
}
