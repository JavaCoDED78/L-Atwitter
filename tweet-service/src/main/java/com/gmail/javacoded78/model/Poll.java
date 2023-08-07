package com.gmail.javacoded78.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "polls")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "polls_seq")
    @SequenceGenerator(name = "polls_seq", sequenceName = "polls_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @OneToOne
    private Tweet tweet;

    @OneToMany
    private List<PollChoice> pollChoices;
}