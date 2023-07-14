package com.gmail.javacoded78.latwitter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bookmarks")
@ToString
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
