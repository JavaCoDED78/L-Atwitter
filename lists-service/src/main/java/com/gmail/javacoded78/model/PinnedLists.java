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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "pinned_lists")
public class PinnedLists {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pinned_lists_seq")
    @SequenceGenerator(name = "pinned_lists_seq", sequenceName = "pinned_lists_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "pinned_date", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime pinnedDate = LocalDateTime.now();

    @NonNull
    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private Lists list;

    @NonNull
    @Column(name = "pinned_user_id", nullable = false)
    private Long pinnedUserId;
}
