package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Lists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ListsRepository extends JpaRepository<Lists, Long> {

    List<Lists> findByIsPrivateFalse();

    List<Lists> findByListOwner_IdAndIsPrivateFalse(Long id);

    List<Lists> findByMembers_Id(Long id);
}
