package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.BlockedUserResponse;
import com.gmail.javacoded78.mapper.BlockUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.constants.PathConstants.UI_V1_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_USER)
public class BlockUserController {

    private final BlockUserMapper blockUserMapper;

    @GetMapping("/blocked")
    public ResponseEntity<List<BlockedUserResponse>> getBlockList(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<BlockedUserResponse> response = blockUserMapper.getBlockList(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/blocked/{userId}")
    public ResponseEntity<Boolean> processBlockList(@PathVariable Long userId) {
        return ResponseEntity.ok(blockUserMapper.processBlockList(userId));
    }
}
