package com.gmail.javacoded78.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "like_tweets")
public class LikeTweet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_tweets_seq")
    @SequenceGenerator(name = "like_tweets_seq", sequenceName = "like_tweets_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "like_tweet_date")
    private LocalDateTime likeTweetDate;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tweets_id")
    private Tweet tweet;

    public LikeTweet() {
        this.likeTweetDate = LocalDateTime.now().withNano(0);
    }
}
