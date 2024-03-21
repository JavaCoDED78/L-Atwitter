package com.gmail.javacoded78.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockUserEvent implements UserEvent {

    private Long id;
    private String fullName;
    private String username;
    private boolean privateProfile;
    private boolean userBlocked;
}
