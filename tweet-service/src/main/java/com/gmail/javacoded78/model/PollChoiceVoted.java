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
        name = "poll_choice_voted",
        indexes = {
                @Index(name = "poll_choice_voted_voted_user_id_idx", columnList = "voted_user_id"),
                @Index(name = "poll_choice_voted_poll_choice_id_idx", columnList = "poll_choice_id"),
        })
public class PollChoiceVoted {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poll_choice_voted_seq")
    @SequenceGenerator(name = "poll_choice_voted_seq", sequenceName = "poll_choice_voted_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @NonNull
    @Column(name = "voted_user_id", nullable = false)
    private Long votedUserId;

    @NonNull
    @Column(name = "poll_choice_id", nullable = false)
    private Long pollChoiceId;
}
