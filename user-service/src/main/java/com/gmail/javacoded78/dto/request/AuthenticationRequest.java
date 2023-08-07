package com.gmail.javacoded78.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_NOT_VALID;
import static com.gmail.javacoded78.constants.ErrorMessage.EMPTY_PASSWORD;
import static com.gmail.javacoded78.constants.ErrorMessage.SHORT_PASSWORD;

@Data
public class AuthenticationRequest {

    @Email(regexp = ".+@.+\\..+", message = EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = EMPTY_PASSWORD)
    @Size(min = 8, message = SHORT_PASSWORD)
    private String password;
}
