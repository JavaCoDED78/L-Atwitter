package com.gmail.javacoded78.repository.projection;

public interface UserCommonProjection {

    Long getId();
    String getEmail();
    String getFullName();
    String getActivationCode();
    String getPasswordResetCode();
}