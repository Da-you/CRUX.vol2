package com.project.crux.domain.member.application;

import com.project.crux.domain.crew.Status;
import com.project.crux.domain.crew.domain.CrewMember;
import com.project.crux.domain.crew.repository.CrewMemberRepository;
import com.project.crux.domain.crew.repository.CrewRepository;
import com.project.crux.domain.member.domain.Member;
import com.project.crux.domain.member.dto.MemberRequestDto.LoginRequestDto;
import com.project.crux.domain.member.dto.MemberRequestDto.SignupRequestDto;
import com.project.crux.global.exception.CustomException;
import com.project.crux.global.exception.ErrorCode;
import com.project.crux.domain.member.repository.MemberRepository;
import com.project.crux.global.security.jwt.TokenProvider;
import com.project.crux.global.security.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.crux.global.Constant.DEFAULT_IMAGE_URL;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CrewMemberRepository crewMemberRepository;
    private final CrewRepository crewRepository;

    //일반회원가입
    @Transactional
    public void signUpMember(SignupRequestDto request) {
        if (checkNickname(request.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
        if (checkEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        Member member = Member.signup(
                request.getEmail(), request.getNickname(),
                request.getPassword(), request.getContent(),
                DEFAULT_IMAGE_URL);

        memberRepository.save(member);
    }

    //이메일 중복 확인
    @Transactional(readOnly = true)
    public boolean checkEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    //닉네임 중복 확인
    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);

    }

    //일반 로그인
    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Member member = isPresentMember(loginRequestDto.getEmail());
        if (!member.validatePassword(passwordEncoder, loginRequestDto.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        String token = tokenProvider.generateToken(member);
        tokenToHeaders(token, response);
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(()
                -> new CustomException(ErrorCode.USER_NOT_FOUND));

    }

    public void tokenToHeaders(String token, HttpServletResponse response) {
        response.addHeader("Access_Token", "Bearer " + token);
    }

    @Transactional
    public void withdraw(UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List<CrewMember> crewMembers = crewMemberRepository.findAllByMember(member).stream()
                .filter(crewMember -> crewMember.getStatus().equals(Status.ADMIN))
                .collect(Collectors.toList());
        for (CrewMember crewMember : crewMembers) {
            crewRepository.delete(crewMember.getCrew());
        }
        memberRepository.delete(member);

    }
}
