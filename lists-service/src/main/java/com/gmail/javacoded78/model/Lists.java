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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "lists", indexes = @Index(name = "lists_list_owner_id_idx", columnList = "list_owner_id"))
public class Lists {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lists_seq")
    @SequenceGenerator(name = "lists_seq", sequenceName = "lists_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "private", columnDefinition = "boolean default false")
    private boolean isPrivate;

    @Column(name = "alt_wallpaper")
    private String altWallpaper;

    @Column(name = "wallpaper")
    private String wallpaper;

    @OneToMany(mappedBy = "list")
    private List<PinnedLists> pinnedLists = new ArrayList<>();

    @Column(name = "list_owner_id", nullable = false)
    private Long listOwnerId;
}
