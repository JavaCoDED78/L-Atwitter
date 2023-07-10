package com.gmail.javacoded78.latwitter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tweets")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "addressed_username")
    private String addressedUsername;

    @Column(name = "addressed_id")
    private Long addressedId;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Image> images;

    @ManyToMany
    @JoinTable(name = "tweet_likes",
            joinColumns = @JoinColumn(name = "tweets_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<User> likes;

    @ManyToMany
    @JoinTable(name = "retweets",
            joinColumns = @JoinColumn(name = "tweets_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<User> retweets;

    @ManyToMany
    @JoinTable(name = "replies",
            joinColumns = @JoinColumn(name = "tweets_id"),
            inverseJoinColumns = @JoinColumn(name = "reply_id"))
    private List<Tweet> replies;

    public Tweet() {
        this.dateTime = LocalDateTime.now().withNano(0);
        this.likes = new ArrayList<>();
        this.retweets = new ArrayList<>();
    }
}
