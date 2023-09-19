package com.gmail.javacoded78.dto.request;

import com.gmail.javacoded78.enums.LinkCoverSize;
import com.gmail.javacoded78.enums.ReplyType;
import com.gmail.javacoded78.dto.response.TweetImageResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TweetRequest {

    private Long id;
    private String text;
    private String addressedUsername;
    private Long addressedId;
    private Long listId;
    private ReplyType replyType;
    private LinkCoverSize linkCoverSize;
    private String gifUrl;
    private List<TweetImageResponse> images;
    private String imageDescription;
    private List<Long> taggedImageUsers;
    private Long pollDateTime;
    private List<String> choices;
    private LocalDateTime scheduledDate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class GifImageRequest {

        private Long id;
        private String url;
        private Long width;
        private Long height;
    }
}
