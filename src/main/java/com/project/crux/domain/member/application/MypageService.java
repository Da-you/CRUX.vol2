package com.project.crux.domain.member.application;

import com.project.crux.domain.crew.Status;
import com.project.crux.domain.crew.domain.Crew;
import com.project.crux.domain.crew.domain.CrewMember;
import com.project.crux.domain.crew.domain.LikeCrew;
import com.project.crux.domain.crew.repository.LikeCrewRepository;
import com.project.crux.domain.crew.repository.CrewMemberRepository;
import com.project.crux.domain.member.domain.Member;
import com.project.crux.domain.member.dto.MemberRequestDto.MypageRequestDto;
import com.project.crux.domain.member.dto.MypageResponseDto;
import com.project.crux.domain.gym.domain.Gym;
import com.project.crux.domain.gym.domain.LikeGym;
import com.project.crux.domain.crew.dto.response.CrewResponseDto;
import com.project.crux.domain.gym.dto.response.LikeGymResponseDto;
import com.project.crux.global.exception.CustomException;
import com.project.crux.global.exception.ErrorCode;
import com.project.crux.domain.gym.repository.LikeGymRepository;
import com.project.crux.domain.member.repository.MemberRepository;
import com.project.crux.global.security.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MypageService {
    //마이 페이지 조회 기능
    private final MemberRepository memberRepository;
    private final LikeGymRepository likeGymRepository;
    private final CrewMemberRepository crewMemberRepository;

    private final LikeCrewRepository likeCrewRepository;

    public MypageResponseDto viewMypage(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<CrewResponseDto> likeCrewList = new ArrayList<>();
        List<LikeCrew> likes = likeCrewRepository.findAllByMember(member);
        for (LikeCrew like : likes) {
            Crew crew = like.getCrew();
            likeCrewList.add(CrewResponseDto.from(crew));
        }

        List<LikeGym> likeGymList = likeGymRepository.findAllByMember(member);
        List<LikeGymResponseDto> likeGymResponseDtos = new ArrayList<>();
        for (LikeGym likeGym : likeGymList) {
            Gym gym = likeGym.getGym();
            likeGymResponseDtos.add(new LikeGymResponseDto(gym));
        }
        List<CrewResponseDto> crewResponseDtos = new ArrayList<>();
        List<CrewMember> crewList = crewMemberRepository.findAllByMember(member);
        for (CrewMember crewMember : crewList) {
            if (!crewMember.getStatus().equals(Status.SUBMIT)) {
                Crew crew = crewMember.getCrew();
                crewResponseDtos.add(CrewResponseDto.from(crew));
            }
        }
        return MypageResponseDto.builder()
                .id(memberId)
                .nickname(member.getNickname())
                .content(member.getContent())
                .imgUrl(member.getImgUrl())
                .crewList(crewResponseDtos)
                .likeGymList(likeGymResponseDtos)
                .likeCrewList(likeCrewList)
                .build();
    }

    @Transactional
    public void editMypage(UserDetailsImpl userDetails, MypageRequestDto mypageRequestDto) {
        Member member = memberRepository.findById(userDetails.getMember().getId()).orElseThrow(()
                -> new CustomException(ErrorCode.USER_NOT_FOUND));
        member.update(mypageRequestDto);
    }
}
