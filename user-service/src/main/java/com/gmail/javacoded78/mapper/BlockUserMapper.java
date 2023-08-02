package com.gmail.javacoded78.mapper;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.BlockedUserResponse;
import com.gmail.javacoded78.repository.projection.BlockedUserProjection;
import com.gmail.javacoded78.service.BlockUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockUserMapper {

    private final BasicMapper basicMapper;
    private final BlockUserService blockUserService;

    public HeaderResponse<BlockedUserResponse> getBlockList(Pageable pageable) {
        Page<BlockedUserProjection> blockList = blockUserService.getBlockList(pageable);
        return basicMapper.getHeaderResponse(blockList, BlockedUserResponse.class);
    }

    public Boolean processBlockList(Long userId) {
        return blockUserService.processBlockList(userId);
    }
}
