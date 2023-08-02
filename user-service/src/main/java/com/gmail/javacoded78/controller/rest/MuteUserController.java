package com.gmail.javacoded78.controller.rest;

import com.gmail.javacoded78.dto.HeaderResponse;
import com.gmail.javacoded78.dto.response.MutedUserResponse;
import com.gmail.javacoded78.mapper.MuteUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.javacoded78.controller.PathConstants.UI_V1_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping(UI_V1_USER)
public class MuteUserController {

    private final MuteUserMapper muteUserMapper;

    @GetMapping("/muted")
    public ResponseEntity<List<MutedUserResponse>> getMutedList(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<MutedUserResponse> response = muteUserMapper.getMutedList(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping("/muted/{userId}")
    public ResponseEntity<Boolean> processMutedList(@PathVariable Long userId) {
        return ResponseEntity.ok(muteUserMapper.processMutedList(userId));
    }
}
