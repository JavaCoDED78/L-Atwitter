package com.gmail.javacoded78.model;

import com.gmail.javacoded78.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_seq")
    @SequenceGenerator(name = "notifications_seq", sequenceName = "notifications_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "date", columnDefinition = "timestamp default current_timestamp")
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "notified_user_id", nullable = false)
    private Long notifiedUserId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_to_follow_id")
    private Long userToFollowId;

    @Column(name = "tweet_id")
    private Long tweetId;

    @Column(name = "list_id")
    private Long listId;
}
