package com.gmail.javacoded78.latwitter.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class ProcessEmailRequest {
    @Email(regexp = ".+@.+\\..+", message = "Please enter a valid email address.")
    private String email;
}
