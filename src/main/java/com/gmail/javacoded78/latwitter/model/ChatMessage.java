package com.gmail.javacoded78.latwitter.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chat_messages")
@ToString(of = {"id"})
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToOne
    private Tweet tweet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public ChatMessage() {
        this.date = LocalDateTime.now().withNano(0);
    }
}
