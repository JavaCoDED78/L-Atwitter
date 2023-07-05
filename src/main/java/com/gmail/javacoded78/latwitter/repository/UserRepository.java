package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findByFullNameContaining(String fullName);

    User findByActivationCode(String code);

    User findByPasswordResetCode(String code);

    List<User> findTop5By();
}
