package com.project.crux.global.infrastructure.kakao.request;

import com.project.crux.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class KakaoMemberInfoDto {
    private Long id;
    private String nickname;
    private String email;

    public KakaoMemberInfoDto(Member member) {
        this.id = member.getKakaoId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
    }
}