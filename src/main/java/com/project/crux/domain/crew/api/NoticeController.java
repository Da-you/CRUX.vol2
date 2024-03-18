package com.project.crux.domain.crew.api;

import com.project.crux.domain.crew.dto.request.NoticeRequestDto;
import com.project.crux.global.common.ResponseDto;
import com.project.crux.domain.crew.dto.response.CrewNoticeResponseDto;
import com.project.crux.domain.crew.application.NoticeService;
import com.project.crux.global.security.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    //api 크루 공지사항 등록
    @PostMapping("/notices/{crewId}")
    public ResponseDto<CrewNoticeResponseDto> createNotice(@PathVariable Long crewId, @RequestBody NoticeRequestDto requestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(noticeService.createNotice(crewId, requestDto, userDetails));
    }

    //api 크루 공지사항 수정
    @PutMapping("/notices/{noticeId}")
    public ResponseDto<CrewNoticeResponseDto> updateNotice(@PathVariable Long noticeId, @RequestBody NoticeRequestDto requestDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(noticeService.updateNotice(noticeId, requestDto, userDetails));
    }

    //api 크루 공지사항 삭제
    @DeleteMapping("/notices/{noticeId}")
    public ResponseDto<?> deleteNotice(@PathVariable Long noticeId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(noticeService.deleteNotice(noticeId, userDetails));
    }

}
