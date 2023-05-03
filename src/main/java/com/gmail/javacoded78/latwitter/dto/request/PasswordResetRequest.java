package com.gmail.javacoded78.latwitter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetRequest {

    private String email;

    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long")
    private String password;

    @Size(min = 6, max = 16, message = "The password confirmation must be between 6 and 16 characters long")
    private String password2;
}
