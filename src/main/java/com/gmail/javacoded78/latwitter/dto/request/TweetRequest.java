package com.gmail.javacoded78.latwitter.dto.request;

import com.gmail.javacoded78.latwitter.model.Image;
import lombok.Data;

import java.util.List;

@Data
public class TweetRequest {

    private String text;
    private String addressedUsername;
    private Long addressedId;
    private List<Image> images;
    private Long pollDateTime;
    private List<String> choices;
}
