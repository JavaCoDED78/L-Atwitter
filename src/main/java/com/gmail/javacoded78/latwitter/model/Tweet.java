package com.gmail.javacoded78.latwitter.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tweets")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private LocalDateTime dateTime;

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

    public Tweet() {
        this.dateTime = LocalDateTime.now().withNano(0);
    }
}
