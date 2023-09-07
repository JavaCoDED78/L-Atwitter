package com.gmail.javacoded78.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserToListsRequest {
    private Long userId;
    private List<ListsRequest> lists;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListsRequest {

        private Long listId;
        private Boolean isMemberInList;
    }
}
