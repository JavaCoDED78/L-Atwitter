package com.gmail.javacoded78.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.gmail.javacoded78.constants.ErrorMessage.BLANK_NAME;
import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_NOT_VALID;
import static com.gmail.javacoded78.constants.ErrorMessage.NAME_NOT_VALID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {

    @Email(regexp = ".+@.+\\..+", message = EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = BLANK_NAME)
    @Size(min = 1, max = 50, message = NAME_NOT_VALID)
    private String username;

    private String birthday;
}
