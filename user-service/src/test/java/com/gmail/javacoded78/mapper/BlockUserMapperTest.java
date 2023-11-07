package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.repository.projection.BlockedUserProjection;
import com.gmail.javacoded78.service.BlockUserService;
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
public class BlockUserMapperTest extends AbstractAuthTest {

    private final BlockUserMapper basicMapper;

    @MockBean
    private final BlockUserService blockUserService;

    @Test
    void getBlockList() {
        Page<BlockedUserProjection> blockedUserProjections = UserServiceTestHelper.createBlockedUserProjections();
        when(blockUserService.getBlockList(pageable)).thenReturn(blockedUserProjections);
        basicMapper.getBlockList(pageable);
        verify(blockUserService, times(1)).getBlockList(pageable);
    }

    @Test
    void processBlockList() {
        when(blockUserService.processBlockList(TestConstants.USER_ID)).thenReturn(true);
        assertTrue(basicMapper.processBlockList(TestConstants.USER_ID));
        verify(blockUserService, times(1)).processBlockList(TestConstants.USER_ID);

    }
}
