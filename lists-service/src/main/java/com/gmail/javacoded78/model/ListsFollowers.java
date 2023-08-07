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

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@RequiredArgsConstructor
@Table(
        name = "lists_followers",
        indexes = {
                @Index(name = "lists_followers_list_id_idx", columnList = "list_id"),
                @Index(name = "lists_followers_follower_id_idx", columnList = "follower_id")
        })
public class ListsFollowers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lists_followers_seq")
    @SequenceGenerator(name = "lists_followers_seq", sequenceName = "lists_followers_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @NonNull
    @Column(name = "list_id", nullable = false)
    private Long listId;

    @NonNull
    @Column(name = "follower_id", nullable = false)
    private Long followerId;
}
