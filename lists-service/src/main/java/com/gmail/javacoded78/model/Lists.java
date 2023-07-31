package com.gmail.javacoded78.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "lists")
public class Lists {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lists_seq")
    @SequenceGenerator(name = "lists_seq", sequenceName = "lists_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "private")
    private boolean isPrivate;

    @Column(name = "alt_wallpaper")
    private String altWallpaper;

    @Column(name = "wallpaper")
    private String wallpaper;

    @OneToMany(mappedBy = "list")
    private List<PinnedLists> pinnedLists;

    @Column(name = "list_owner_id")
    private Long listOwnerId;
}
