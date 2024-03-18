package com.project.crux.domain.gym.application;

import com.project.crux.domain.gym.repository.GymRepository;
import com.project.crux.domain.gym.repository.ReviewRepository;
import com.project.crux.domain.gym.domain.Gym;
import com.project.crux.domain.gym.domain.LikeGym;
import com.project.crux.domain.gym.domain.Review;
import com.project.crux.domain.gym.domain.ReviewPhoto;
import com.project.crux.domain.gym.dto.response.GymResponseDto;
import com.project.crux.domain.gym.dto.response.ReviewPhotoResponseDto;
import com.project.crux.domain.gym.dto.response.ReviewResponseDto;
import com.project.crux.global.exception.CustomException;
import com.project.crux.global.exception.ErrorCode;
import com.project.crux.domain.gym.repository.LikeGymRepository;
import com.project.crux.domain.gym.repository.ReviewPhotoRepository;
import com.project.crux.domain.member.domain.Member;
import com.project.crux.global.security.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;
    private final LikeGymRepository likeGymRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;


    public List<GymResponseDto> getGyms(String lat, String lon, Pageable pageable) {

        Page<Gym> gyms = gymRepository.findByLatAndLon(lat, lon, pageable);

        return pageToDtoList(gyms);
    }

    public List<GymResponseDto> getPopularGyms(Long lastArticleId, int size) {

        if (lastArticleId == null) {
            PageRequest pageRequest = PageRequest.of(0, size, Sort.by("avgScore").descending());
            Page<Gym> gyms = gymRepository.findAll(pageRequest);
            return pageToDtoList(gyms);
        }

        PageRequest pageRequest = PageRequest.of(0, size);
        Gym gym = getGymById(lastArticleId);
        double customCursor = generateCustomCursor(gym.getAvgScore(), gym.getId());
        Page<Gym> gyms = gymRepository.findByCustomCursor(customCursor, pageRequest);
        return pageToDtoList(gyms);
    }


    public List<GymResponseDto> getSearchGyms(String query, Long lastArticleId, int size) {

        PageRequest pageRequest = PageRequest.of(0, size, Sort.by("id").descending());

        Page<Gym> gyms = gymRepository.findByIdLessThanAndNameContains(lastArticleId, query, pageRequest);

        return pageToDtoList(gyms);
    }


    public GymResponseDto getGym(Long gymId) {

        Gym gym = getGymById(gymId);
        GymResponseDto gymResponseDto = GymResponseDto.from(gym);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getCredentials().equals("")) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Member member = userDetails.getMember();

            Optional<LikeGym> likeGym = likeGymRepository.findByMemberAndGym(member, gym);
            gymResponseDto.setLikeGym(likeGym.isPresent());;
        }

        gymResponseDto.setLikeNum(likeGymRepository.countLikeGymByGym(gym));
        List<Review> reviewList = reviewRepository.findByGym(gym);
        List<ReviewResponseDto> reviews = gymResponseDto.getReviews();
        getReviewResponseList(reviewList, reviews);
        return gymResponseDto;
    }

    @Transactional
    public String likeGym(UserDetailsImpl userDetails, Long gymId) {

        Member member = userDetails.getMember();
        Gym gym = getGymById(gymId);

        Optional<LikeGym> existLike = likeGymRepository.findByMemberAndGym(member, gym);

        if (!existLike.isPresent()) {
            LikeGym likeGym = new LikeGym(member,gym);
            likeGymRepository.save(likeGym);

            return "즐겨 찾기 추가 완료";
        }
        likeGymRepository.delete(existLike.get());

        return "즐겨 찾기 삭제 완료";
    }

   private void getReviewResponseList(List<Review> reviewList, List<ReviewResponseDto> reviews) {
        reviewList.forEach(review -> {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review.getMember(),review);
            List<ReviewPhoto> reviewPhotoList = reviewPhotoRepository.findAllByReview(review);
            reviewPhotoList.forEach(reviewPhoto -> reviewResponseDto.getReviewPhotoList().add(new ReviewPhotoResponseDto(reviewPhoto)));
            reviews.add(reviewResponseDto);
        });
    }

    private List<GymResponseDto> pageToDtoList(Page<Gym> gyms) {
        List<GymResponseDto> gymResponseDtos = new ArrayList<>();
        gyms.getContent().forEach(gym -> gymResponseDtos.add(GymResponseDto.from(gym)));
        return gymResponseDtos;
    }

    private Gym getGymById(Long gymId) {
        return gymRepository.findById(gymId).orElseThrow(() -> new CustomException(ErrorCode.GYM_NOT_FOUND));
    }

    private double generateCustomCursor(double avgScore, Long gymId) {
        return avgScore + gymId / 100000.0;
    }
}
