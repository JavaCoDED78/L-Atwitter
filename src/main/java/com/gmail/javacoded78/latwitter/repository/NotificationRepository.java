package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
