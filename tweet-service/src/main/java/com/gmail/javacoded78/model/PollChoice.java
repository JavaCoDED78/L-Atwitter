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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "poll_choices")
public class PollChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poll_choices_seq")
    @SequenceGenerator(name = "poll_choices_seq", sequenceName = "poll_choices_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "choice", nullable = false)
    private String choice;
}
