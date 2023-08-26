package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.TagResponse;
import com.gmail.javacoded78.model.Tag;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
class TagMapperTest {

    private final ModelMapper modelMapper;

    @Test
    void convertToResponse() {
        Tag tag = Tag.builder()
                .id(1L)
                .tagName("Test_tag")
                .tweetsQuantity(333L)
                .build();

        TagResponse actualResponse = modelMapper.map(tag, TagResponse.class);
        assertEquals(tag.getId(), actualResponse.getId());
        assertEquals(tag.getTagName(), actualResponse.getTagName());
        assertEquals(tag.getTweetsQuantity(), actualResponse.getTweetsQuantity());
    }
}