package com.project.crux.member.controller;

import com.project.crux.member.domain.request.MypageRequestDto;
import com.project.crux.common.ResponseDto;
import com.project.crux.security.jwt.UserDetailsImpl;
import com.project.crux.member.service.MypageService;
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
    public ResponseDto<?> viewMypage(@PathVariable Long memberId) {
        return mypageService.viewMypage(memberId);
    }

    //마이 페이지 수정
    @PutMapping
    public void editMypage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody MypageRequestDto mypageRequestDto) {
        mypageService.editMypage(userDetails, mypageRequestDto);
    }
}
