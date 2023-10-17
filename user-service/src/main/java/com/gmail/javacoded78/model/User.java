package com.gmail.javacoded78.model;

import com.gmail.javacoded78.enums.BackgroundColorType;
import com.gmail.javacoded78.enums.ColorSchemeType;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id", "email"})
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "location")
    private String location;

    @Column(name = "about")
    private String about;

    @Column(name = "website")
    private String website;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "country")
    private String country;

    @Column(name = "gender")
    private String gender;

    @Column(name = "language")
    private String language;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "registration_date", columnDefinition = "timestamp default current_timestamp")
    @Builder.Default
    private LocalDateTime registrationDate = LocalDateTime.now();

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "password_reset_code")
    private String passwordResetCode;

    @Column(name = "role", columnDefinition = "varchar(255) default 'USER'")
    @Builder.Default
    private String role = "USER";

    @Column(name = "tweet_count", columnDefinition = "int8 default 0")
    @Builder.Default
    private Long tweetCount = 0L;

    @Column(name = "media_tweet_count", columnDefinition = "int8 default 0")
    @Builder.Default
    private Long mediaTweetCount = 0L;

    @Column(name = "like_count", columnDefinition = "int8 default 0")
    @Builder.Default
    private Long likeCount = 0L;

    @Column(name = "notifications_count", columnDefinition = "int8 default 0")
    @Builder.Default
    private Long notificationsCount = 0L;

    @Column(name = "mentions_count", columnDefinition = "int8 default 0")
    @Builder.Default
    private Long mentionsCount = 0L;

    @Column(name = "active", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean active = false;

    @Column(name = "profile_customized", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean profileCustomized = false;

    @Column(name = "profile_started", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean profileStarted = false;

    @Column(name = "muted_direct_messages", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean mutedDirectMessages = false;

    @Column(name = "private_profile", columnDefinition = "boolean default false")
    @Builder.Default
    private boolean privateProfile = false;

    @Column(name = "background_color", columnDefinition = "varchar(255) default 'DEFAULT'")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BackgroundColorType backgroundColor = BackgroundColorType.DEFAULT;

    @Column(name = "color_scheme", columnDefinition = "varchar(255) default 'BLUE'")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ColorSchemeType colorScheme = ColorSchemeType.BLUE;

    @Column(name = "pinned_tweet_id")
    private Long pinnedTweetId;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "wallpaper")
    private String wallpaper;

    @Column(name = "unread_messages_count", columnDefinition = "int8 default 0")
    @Builder.Default
    private Long unreadMessagesCount = 0L;

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "user_muted",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "muted_user_id"))
    private List<User> userMutedList = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "user_blocked",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "blocked_user_id"))
    private List<User> userBlockedList = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private List<User> followers = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> following = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "user_follower_requests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<User> followerRequests = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    @JoinTable(name = "subscribers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private List<User> subscribers = new ArrayList<>();
}
