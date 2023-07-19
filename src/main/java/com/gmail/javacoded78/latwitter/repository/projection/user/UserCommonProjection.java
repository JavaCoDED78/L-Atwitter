package com.gmail.javacoded78.latwitter.repository.projection.user;

public interface UserCommonProjection {

    Long getId();

    String getEmail();

    String getFullName();

    String getActivationCode();

    String getPasswordResetCode();
}
