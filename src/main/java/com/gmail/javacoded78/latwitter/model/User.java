package com.gmail.javacoded78.latwitter.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(of = "email")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldNameConstants
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private String username;
    private String location;
    private String about;
    private String website;
    private String activationCode;
    private String passwordResetCode;
    private String role;
    private boolean active;

    @OneToMany
    private List<Tweet> tweets;
}
