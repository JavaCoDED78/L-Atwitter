package com.gmail.javacoded78.latwitter.dto.request;

import com.gmail.javacoded78.latwitter.model.Image;
import lombok.Data;

import java.util.List;

@Data
public class TweetRequest {

    private String text;
    private List<Image> images;
}
