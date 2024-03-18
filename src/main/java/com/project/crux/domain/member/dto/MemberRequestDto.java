package com.project.crux.domain.member.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberRequestDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SignupRequestDto {
        @Email(message = "올바른 이메일 형식이 아닙니다")
        @NotBlank(message = "이메일은 필수 입력 값입니다")
        private String email;

        @NotBlank(message = "닉네임은 필수 입력 값입니다")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        private String nickname;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,12}", message = "비밀번호는 4~12자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        @NotBlank(message = "자기소개는 필수 입력 값입니다.")
        @Length(max = 150)
        private String content;
    }

    @Getter
    public static class EmailRequestDto {
        @Email(message = "올바른 이메일 형식이 아닙니다")
        @NotBlank(message = "이메일은 필수 입력 값입니다")
        private String email;
    }

    @Getter
    public static class NicknameRequestDto {
        @NotBlank(message = "닉네임은 필수 입력 값입니다")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        private String nickname;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class LoginRequestDto {
        @Email(message = "올바른 이메일 형식이 아닙니다")
        @NotBlank(message = "이메일은 필수 입력 값입니다")
        private String email;
        @NotBlank(message = "비밀번호는 필수 입력 값입니다")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageRequestDto {

        @NotBlank(message = "닉네임은 필수 입력 값입니다")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        private String nickname;

        @NotBlank(message = "유저 프로필 이미지는 필수 입니다.")
        private String imgUrl;

        @NotBlank(message = "자기소개는 필수 입력 값입니다.")
        @Length(max = 150)
        private String content;
    }
}
