package com.gmail.javacoded78.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Column(name = "pinned_date")
    private LocalDateTime pinnedDate;

    @Column(name = "alt_wallpaper")
    private String altWallpaper;

    @OneToOne
    @JoinColumn(name = "wallpaper_id")
    private Image wallpaper;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User listOwner;

    @ManyToMany
    private List<Tweet> tweets;

    @ManyToMany
    private List<User> members;

    @ManyToMany
    private List<User> followers;

    public Lists() {
        this.tweets = new ArrayList<>();
        this.members = new ArrayList<>();
        this.followers = new ArrayList<>();
    }
}
