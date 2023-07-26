package com.gmail.javacoded78.repository.projection.lists;

import org.springframework.beans.factory.annotation.Value;

public interface ListsOwnerMemberProjection {

    ListMemberProjection getMember();

    Long getListId();

    @Value("#{target.member != null ? @listsServiceImpl.isListIncludeUser(target.listId, target.member.id) : false}")
    boolean getIsMemberInList();
}
