package com.gmail.javacoded78.latwitter.dto.request;

import com.gmail.javacoded78.latwitter.enums.BackgroundColorType;
import com.gmail.javacoded78.latwitter.enums.ColorSchemeType;
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
    private BackgroundColorType backgroundColor;
    private ColorSchemeType colorScheme;
}
