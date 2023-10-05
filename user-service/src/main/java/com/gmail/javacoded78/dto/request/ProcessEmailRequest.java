package com.gmail.javacoded78.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

import static com.gmail.javacoded78.constants.ErrorMessage.EMAIL_NOT_VALID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessEmailRequest {

    @Email(regexp = ".+@.+\\..+", message = EMAIL_NOT_VALID)
    private String email;
}
