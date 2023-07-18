package com.gmail.javacoded78.latwitter.repository.projection;

public interface UserPrincipalProjection {

    Long getId();

    String getEmail();

    String getPassword();

    String getActivationCode();
}
