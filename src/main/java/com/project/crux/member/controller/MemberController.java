package com.project.crux.member.controller;

import com.project.crux.member.domain.request.EmailRequestDto;
import com.project.crux.member.domain.request.LoginRequestDto;
import com.project.crux.member.domain.request.NicknameRequestDto;
import com.project.crux.member.domain.request.SignupRequestDto;
import com.project.crux.common.ResponseDto;
import com.project.crux.security.jwt.UserDetailsImpl;
import com.project.crux.member.service.KakaoMemberService;
import com.project.crux.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final KakaoMemberService kakaoMemberService;

    //일반 회원가입
    @PostMapping("/signup")
    public void signUpMember(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        memberService.signUpMember(signupRequestDto);
    }

    //이메일 중복 확인
    @GetMapping("/email-check")
    public boolean checkEmail(@ModelAttribute @Valid EmailRequestDto emailRequestDto) {
        return memberService.checkEmail(emailRequestDto.getEmail());
    }

    //닉네임 중복 확인
    @GetMapping("/nickname-check")
    public ResponseDto<?> checkNickname(@ModelAttribute @Valid NicknameRequestDto nicknameRequestDto) {
        return memberService.checkNickname(nicknameRequestDto.getNickname());
    }

    //일반 로그인
    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDto loginRequestDto,
                                HttpServletResponse response) {
         memberService.login(loginRequestDto, response);
    }

    //일반 회원 탈퇴
    @DeleteMapping("/withdraw")
    public void withdraw(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberService.withdraw(userDetails);
    }

    //카카오 로그인
    @GetMapping("/oauth/kakao/callback")
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws IOException {
        kakaoMemberService.kakaoLogin(code, response);
    }
}
