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
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "topic_followers")
public class TopicFollowers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_followers_seq")
    @SequenceGenerator(name = "topic_followers_seq", sequenceName = "topic_followers_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @NonNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NonNull
    @Column(name = "topic_id", nullable = false)
    private Long topicId;
}
