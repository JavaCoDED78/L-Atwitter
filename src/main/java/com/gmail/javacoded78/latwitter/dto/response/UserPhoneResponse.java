package com.gmail.javacoded78.latwitter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserPhoneResponse {

    private String countryCode;
    private Long phone;
}
