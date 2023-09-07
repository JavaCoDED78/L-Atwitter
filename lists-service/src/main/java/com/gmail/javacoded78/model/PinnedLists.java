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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "pinned_lists",
        indexes = {
                @Index(name = "pinned_lists_list_id_idx", columnList = "list_id"),
                @Index(name = "pinned_lists_pinned_user_id_idx", columnList = "pinned_user_id")
        })
public class PinnedLists {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pinned_lists_seq")
    @SequenceGenerator(name = "pinned_lists_seq", sequenceName = "pinned_lists_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "pinned_date", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime pinnedDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private Lists list;

    @Column(name = "pinned_user_id", nullable = false)
    private Long pinnedUserId;
}
