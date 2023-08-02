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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "tweet_tags")
public class TweetTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tweet_tags_seq")
    @SequenceGenerator(name = "tweet_tags_seq", sequenceName = "tweet_tags_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @NonNull
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @NonNull
    @Column(name = "tweet_id", nullable = false)
    private Long tweetId;
}