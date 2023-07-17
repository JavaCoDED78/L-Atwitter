package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE chats_participants SET left_chat = true WHERE id = ?1 AND chat_id = ?2", nativeQuery = true)
    void leaveFromConversation(Long participantId, Long chatId);
}
