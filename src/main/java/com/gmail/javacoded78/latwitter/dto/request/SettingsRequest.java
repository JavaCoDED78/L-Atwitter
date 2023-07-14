package com.gmail.javacoded78.latwitter.dto.request;

import lombok.Data;

@Data
public class SettingsRequest {

    private String username;
    private String email;
    private String countryCode;
    private Long phone;
    private String country;
    private String gender;
    private String language;
    private boolean mutedDirectMessages;
    private boolean privateProfile;
}
