package com.project.crux.domain.member.api;

import com.project.crux.global.common.ResponseDto;
import com.project.crux.domain.member.dto.MemberRequestDto.MypageRequestDto;
import com.project.crux.domain.member.dto.MypageResponseDto;
import com.project.crux.global.security.jwt.UserDetailsImpl;
import com.project.crux.domain.member.application.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mypages")

@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    //마이 페이지 조회
    @GetMapping("/{memberId}")
    public ResponseDto<MypageResponseDto> viewMypage(@PathVariable Long memberId) {
        return ResponseDto.success(mypageService.viewMypage(memberId));
    }

    //마이 페이지 수정
    @PutMapping
    public void editMypage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody MypageRequestDto mypageRequestDto) {
        mypageService.editMypage(userDetails, mypageRequestDto);
    }
}
