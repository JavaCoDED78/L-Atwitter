package com.gmail.javacoded78.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@Builder
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
    @Builder.Default
    private LocalDateTime likeTweetDate = LocalDateTime.now();

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "tweet_id", nullable = false)
    private Long tweetId;
}
