package com.gmail.javacoded78.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPhoneResponse {

    private String countryCode;
    private Long phone;
}
