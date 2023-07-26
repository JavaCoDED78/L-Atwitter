package com.gmail.javacoded78.model;

import com.gmail.javacoded78.enums.TopicCategory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topics_seq")
    @SequenceGenerator(name = "topics_seq", sequenceName = "topics_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "topic_name")
    private String topicName;

    @Column(name = "topic_category")
    @Enumerated(EnumType.STRING)
    private TopicCategory topicCategory;

//    @ManyToMany
//    @JoinTable(name = "topic_followers",
//            joinColumns = @JoinColumn(name = "topic_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<User> topicFollowers;
//
//    @ManyToMany
//    @JoinTable(name = "topic_not_interested",
//            joinColumns = @JoinColumn(name = "topic_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    private List<Use
}