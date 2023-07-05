package com.gmail.javacoded78.latwitter.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String tagName;
    private Long tweetsQuantity;

    @ManyToMany
    @JoinTable(name = "tweets_tags",
            joinColumns = @JoinColumn(name = "tags_id"),
            inverseJoinColumns = @JoinColumn(name = "tweets_id"))
    private List<Tweet> tweets;
}
