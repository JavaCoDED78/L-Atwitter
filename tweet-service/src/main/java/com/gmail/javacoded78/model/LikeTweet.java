package com.gmail.javacoded78.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@RequiredArgsConstructor
@Table(
        name = "liked_tweets",
        indexes = {
                @Index(name = "liked_tweets_user_id_idx", columnList = "user_id"),
                @Index(name = "liked_tweets_tweet_id_idx", columnList = "tweet_id"),
        })
public class LikeTweet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "liked_tweets_seq")
    @SequenceGenerator(name = "liked_tweets_seq", sequenceName = "liked_tweets_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "liked_tweet_date", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime likeTweetDate = LocalDateTime.now();

    @NonNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NonNull
    @Column(name = "tweet_id", nullable = false)
    private Long tweetId;
}
