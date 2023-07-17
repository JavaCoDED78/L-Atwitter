package com.gmail.javacoded78.latwitter.repository;

import com.gmail.javacoded78.latwitter.model.Tweet;
import com.gmail.javacoded78.latwitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByActiveTrueAndIdNot(Long id);

    User findByEmail(String email);

    List<User> findByFullNameOrUsernameContainingIgnoreCase(
            @Param("fullName") String fullName,
            @Param("username") String username);

    Optional<User> findByActivationCode(String code);

    Optional<User> findByPasswordResetCode(String code);

    List<User> findTop5ByActiveTrue();

    List<User> findByUnreadMessages_Tweet(Tweet tweet);
}
