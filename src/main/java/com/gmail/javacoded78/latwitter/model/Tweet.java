package com.gmail.javacoded78.latwitter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Tweet {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String text;
    private LocalDateTime dateTime;

    @ManyToOne
    private User user;

    public Tweet() {
        this.dateTime = LocalDateTime.now().withNano(0);
    }
}
