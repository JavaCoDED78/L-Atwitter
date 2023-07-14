package com.gmail.javacoded78.latwitter.model;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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

    @Column(name = "text", length = 1337, columnDefinition = "text")
    private String text;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "scheduled_date")
    private LocalDateTime scheduledDate;

    @Column(name = "addressed_username")
    private String addressedUsername;

    @Column(name = "addressed_id")
    private Long addressedId;

    @Column(name = "addressed_tweet_id")
    private Long addressedTweetId;

    @Column(name = "reply_type")
    @Enumerated(EnumType.STRING)
    private ReplyType replyType;

    @Column(name = "link")
    private String link;

    @Column(name = "link_title")
    private String linkTitle;

    @Column(name = "link_description")
    private String linkDescription;

    @Column(name = "link_cover")
    private String linkCover;

    @Column(name = "link_cover_size")
    @Enumerated(EnumType.STRING)
    private LinkCoverSize linkCoverSize;

    @OneToOne
    @JoinTable(name = "tweet_quote",
            joinColumns = @JoinColumn(name = "tweets_id"),
            inverseJoinColumns = @JoinColumn(name = "quote_tweet_id"))
    private Tweet quoteTweet;

    @OneToOne
    @JoinTable(name = "tweet_pool",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "pool_id"))
    private Poll poll;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Image> images;

    @OneToMany(mappedBy = "tweet")
    private List<LikeTweet> likedTweets;

    @OneToMany(mappedBy = "tweet")
    private List<Retweet> retweets;

    @ManyToMany
    @JoinTable(name = "replies",
            joinColumns = @JoinColumn(name = "tweets_id"),
            inverseJoinColumns = @JoinColumn(name = "reply_id"))
    private List<Tweet> replies;

    public Tweet() {
        this.dateTime = LocalDateTime.now().withNano(0);
        this.likedTweets = new ArrayList<>();
        this.retweets = new ArrayList<>();
    }
}
