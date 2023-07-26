package com.gmail.javacoded78.model;

import com.gmail.javacoded78.enums.NotificationType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_seq")
    @SequenceGenerator(name = "notifications_seq", sequenceName = "notifications_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne
    @JoinColumn(name = "notified_user_id")
    private User notifiedUser;

    @OneToOne
    private User user;

    @OneToOne
    private User userToFollow;

    @OneToOne
    private Tweet tweet;

    @OneToOne
    private Lists list;

    public Notification() {
        this.date = LocalDateTime.now().withNano(0);
    }
}
