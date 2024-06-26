package com.gmail.javacoded78.dto.response.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTweetResponse {

    private Long id;
    private String text;
    private Long authorId;
    private boolean notificationCondition;
}
