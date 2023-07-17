package com.gmail.javacoded78.latwitter.model;

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
@Table(name = "bookmarks")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bookmark_date")
    private LocalDateTime bookmarkDate;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @OneToOne
    private Tweet tweet;

    public Bookmark() {
        this.bookmarkDate = LocalDateTime.now().withNano(0);
    }
}
