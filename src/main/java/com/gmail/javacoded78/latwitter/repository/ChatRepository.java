package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
