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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "lists_members",
        indexes = {
                @Index(name = "lists_members_list_id_idx", columnList = "list_id"),
                @Index(name = "lists_members_member_id_idx", columnList = "member_id")
        })
public class ListsMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lists_members_seq")
    @SequenceGenerator(name = "lists_members_seq", sequenceName = "lists_members_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "list_id", nullable = false)
    private Long listId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;
}