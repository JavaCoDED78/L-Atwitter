package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.models.PollChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollChoiceRepository extends JpaRepository<PollChoice, Long> {
}
