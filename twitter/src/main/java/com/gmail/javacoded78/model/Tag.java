package com.gmail.javacoded78.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_seq")
    @SequenceGenerator(name = "tags_seq", sequenceName = "tags_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "tweets_quantity")
    private Long tweetsQuantity;

    @ManyToMany
    @JoinTable(name = "tweets_tags",
            joinColumns = @JoinColumn(name = "tags_id"),
            inverseJoinColumns = @JoinColumn(name = "tweets_id"))
    private List<Tweet> tweets;
}
