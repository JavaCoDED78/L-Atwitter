package com.gmail.javacoded78.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_NOT_VALID;
import static com.gmail.javacoded78.constants.ErrorMessage.EMPTY_PASSWORD_CONFIRMATION;
import static com.gmail.javacoded78.constants.ErrorMessage.SHORT_PASSWORD;

@Data
public class EndRegistrationRequest {

    @Email(regexp = ".+@.+\\..+", message = EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = EMPTY_PASSWORD_CONFIRMATION)
    @Size(min = 8, message = SHORT_PASSWORD)
    private String password;
}
