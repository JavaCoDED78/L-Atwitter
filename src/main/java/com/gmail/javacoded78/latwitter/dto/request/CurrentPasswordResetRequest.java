package com.gmail.javacoded78.latwitter.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CurrentPasswordResetRequest {

    @NotBlank(message = "Current password cannot be empty.")
    private String currentPassword;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, message = "Your password needs to be at least 8 characters. Please enter a longer one.")
    private String password;

    @NotBlank(message = "Password confirmation cannot be empty.")
    @Size(min = 8, message = "Your password needs to be at least 8 characters. Please enter a longer one.")
    private String password2;
}
