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
        name = "bookmarks",
        indexes = {
                @Index(name = "bookmarks_user_id_idx", columnList = "user_id"),
                @Index(name = "bookmarks_tweet_id_idx", columnList = "tweet_id"),
        })
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookmarks_seq")
    @SequenceGenerator(name = "bookmarks_seq", sequenceName = "bookmarks_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "bookmark_date", columnDefinition = "timestamp default current_timestamp")
    @Builder.Default
    private LocalDateTime bookmarkDate = LocalDateTime.now();

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "tweet_id", nullable = false)
    private Long tweetId;
}
