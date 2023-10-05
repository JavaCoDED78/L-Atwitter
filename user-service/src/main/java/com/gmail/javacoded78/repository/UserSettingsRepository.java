package com.gmail.javacoded78.repository;

import com.gmail.javacoded78.enums.BackgroundColorType;
import com.gmail.javacoded78.enums.ColorSchemeType;
import com.gmail.javacoded78.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingsRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT CASE WHEN count(u) > 0 THEN true
                ELSE false END
            FROM User u
            WHERE u.email = :email
            """)
    boolean isEmailExist(@Param("email") String email);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.email = :email
            WHERE u.id = :userId
            """)
    void updateEmail(@Param("email") String email, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.username = :username
            WHERE u.id = :userId
            """)
    void updateUsername(@Param("username") String username, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.countryCode = :countryCode, u.phone = :phone
            WHERE u.id = :userId
            """)
    void updatePhone(@Param("countryCode") String countryCode, @Param("phone") Long phone, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.country = :country
            WHERE u.id = :userId
            """)
    void updateCountry(@Param("country") String country, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.gender = :gender
            WHERE u.id = :userId
            """)
    void updateGender(@Param("gender") String gender, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.language = :language
            WHERE u.id = :userId
            """)
    void updateLanguage(@Param("language") String language, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.mutedDirectMessages = :mutedDirectMessages
            WHERE u.id = :userId
            """)
    void updateDirectMessageRequests(@Param("mutedDirectMessages") boolean mutedDirectMessages, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.privateProfile = :privateProfile
            WHERE u.id = :userId
            """)
    void updatePrivateProfile(@Param("privateProfile") boolean privateProfile, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.colorScheme = :colorSchemeType
            WHERE u.id = :userId
            """)
    void updateColorScheme(@Param("colorSchemeType") ColorSchemeType colorSchemeType, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.backgroundColor = :backgroundColor
            WHERE u.id = :userId
            """)
    void updateBackgroundColor(@Param("backgroundColor") BackgroundColorType backgroundColorType, @Param("userId") Long userId);
}
