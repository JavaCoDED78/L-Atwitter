package com.gmail.javacoded78.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetDeleteRequest {

    private List<Long> tweetsIds;
}
