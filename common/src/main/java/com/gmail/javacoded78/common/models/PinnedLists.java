package com.gmail.javacoded78.common.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "pinned_lists")
public class PinnedLists {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pinned_date")
    private LocalDateTime pinnedDate;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private Lists list;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User pinnedUser;
}