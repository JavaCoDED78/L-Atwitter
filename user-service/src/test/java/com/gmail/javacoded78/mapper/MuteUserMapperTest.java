package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.repository.projection.MutedUserProjection;
import com.gmail.javacoded78.service.MuteUserService;
import com.gmail.javacoded78.service.UserServiceTestHelper;
import com.gmail.javacoded78.util.AbstractAuthTest;
import com.gmail.javacoded78.util.TestConstants;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class MuteUserMapperTest extends AbstractAuthTest {

    private final MuteUserMapper muteUserMapper;

    @MockBean
    private final MuteUserService muteUserService;

    @Test
    void getMutedList() {
        Page<MutedUserProjection> mutedUserProjections = UserServiceTestHelper.createMutedUserProjections();
        when(muteUserService.getMutedList(pageable)).thenReturn(mutedUserProjections);
        muteUserMapper.getMutedList(pageable);
        verify(muteUserService, times(1)).getMutedList(pageable);
    }

    @Test
    void processMutedList() {
        when(muteUserService.processMutedList(TestConstants.USER_ID)).thenReturn(true);
        assertTrue(muteUserMapper.processMutedList(TestConstants.USER_ID));
        verify(muteUserService, times(1)).processMutedList(TestConstants.USER_ID);
    }
}
