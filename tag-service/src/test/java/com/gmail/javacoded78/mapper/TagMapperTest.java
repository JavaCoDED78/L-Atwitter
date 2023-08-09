package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.TagResponse;
import com.gmail.javacoded78.model.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TagMapperTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void convertToResponse() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setTagName("Test_tag");
        tag.setTweetsQuantity(111L);

        TagResponse orderResponse = modelMapper.map(tag, TagResponse.class);
        assertEquals(tag.getId(), orderResponse.getId());
        assertEquals(tag.getTagName(), orderResponse.getTagName());
        assertEquals(tag.getTweetsQuantity(), orderResponse.getTweetsQuantity());
    }
}