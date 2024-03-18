package com.project.crux.domain.member.dto;

import com.project.crux.domain.crew.dto.response.CrewResponseDto;
import com.project.crux.domain.gym.dto.response.LikeGymResponseDto;
import com.project.crux.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MypageResponseDto {
    private Long id;
    private String nickname;
    private String content;
    private List<CrewResponseDto> crewList;
    private List<LikeGymResponseDto> likeGymList;
    private List<CrewResponseDto> likeCrewList;
    private String imgUrl;

    @Builder
    public MypageResponseDto(Member member, List<CrewResponseDto> crewList, List<LikeGymResponseDto> likeGymList, List<CrewResponseDto> likeCrewList) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.content = member.getContent();
        this.imgUrl = member.getImgUrl();
        this.crewList = crewList;
        this.likeGymList = likeGymList;
        this.likeCrewList = likeCrewList;
    }
}
