package com.gmail.javacoded78.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@Table(
        name = "chats_participants",
        indexes = {
                @Index(name = "chats_participants_user_id_idx", columnList = "user_id"),
                @Index(name = "chats_participants_chat_id_idx", columnList = "chat_id"),
        })
public class ChatParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chats_participants_seq")
    @SequenceGenerator(name = "chats_participants_seq", sequenceName = "chats_participants_seq", initialValue = 100, allocationSize = 1)
    private Long id;

    @Column(name = "left_chat", columnDefinition = "boolean default false")
    private boolean leftChat = false;

    @NonNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;
}