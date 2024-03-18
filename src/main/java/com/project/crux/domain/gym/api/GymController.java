package com.project.crux.domain.gym.api;

import com.project.crux.global.common.ResponseDto;
import com.project.crux.domain.gym.application.GymService;
import com.project.crux.global.security.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequiredArgsConstructor
public class GymController {

    private final GymService gymService;

    //api 내 주변 클라이밍짐 조회

    @GetMapping("/gyms")
    public ResponseDto<?> getGyms(@RequestParam String lat, @RequestParam String lon,
                                  @PageableDefault Pageable pageable) {
        return ResponseDto.success(gymService.getGyms(lat, lon, pageable));
    }

    //api 인기 클라이밍짐 조회
    @GetMapping("/gyms/popular")
    public ResponseDto<?> getPopularGyms(@Min(1) @RequestParam(required = false, defaultValue = "") Long lastArticleId , @RequestParam int size) {
        return ResponseDto.success(gymService.getPopularGyms(lastArticleId, size));
    }

    //api 클라이밍짐 검색 조회
    @GetMapping("/gyms/search")
    public ResponseDto<?> getSearchGyms(@RequestParam String query, @Min(1) @RequestParam Long lastArticleId, @RequestParam int size) {
        return ResponseDto.success(gymService.getSearchGyms(query,lastArticleId,size));
    }


    //api 짐 상세 조회
    @GetMapping("/gyms/{gymId}")
    public ResponseDto<?> getGym(@PathVariable Long gymId) {
        return ResponseDto.success(gymService.getGym(gymId));
    }

    //api 짐 즐겨찾기 추가,삭제
    @PostMapping("/gyms/{gymId}/like")
    public ResponseDto<?> likeGym(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @PathVariable Long gymId){
        return ResponseDto.success(gymService.likeGym(userDetails,gymId));
    }
}
