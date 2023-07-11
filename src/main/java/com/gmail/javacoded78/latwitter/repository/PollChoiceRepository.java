package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.PollChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollChoiceRepository extends JpaRepository<PollChoice, Long> {
}
