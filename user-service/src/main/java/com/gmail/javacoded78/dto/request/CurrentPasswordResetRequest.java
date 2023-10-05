package com.gmail.javacoded78.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.gmail.javacoded78.constants.ErrorMessage.EMPTY_CURRENT_PASSWORD;
import static com.gmail.javacoded78.constants.ErrorMessage.EMPTY_PASSWORD;
import static com.gmail.javacoded78.constants.ErrorMessage.EMPTY_PASSWORD_CONFIRMATION;
import static com.gmail.javacoded78.constants.ErrorMessage.SHORT_PASSWORD;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentPasswordResetRequest {

    @NotBlank(message = EMPTY_CURRENT_PASSWORD)
    private String currentPassword;

    @NotBlank(message = EMPTY_PASSWORD)
    @Size(min = 8, message = SHORT_PASSWORD)
    private String password;

    @NotBlank(message = EMPTY_PASSWORD_CONFIRMATION)
    @Size(min = 8, message = SHORT_PASSWORD)
    private String password2;
}
