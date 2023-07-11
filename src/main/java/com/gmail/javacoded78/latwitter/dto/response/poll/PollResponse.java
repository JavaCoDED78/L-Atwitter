package com.gmail.javacoded78.latwitter.dto.response.poll;

import com.gmail.javacoded78.latwitter.dto.response.poll.PollChoiceResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PollResponse {

    private Long id;
    private LocalDateTime dateTime;
    private List<PollChoiceResponse> pollChoices;
}