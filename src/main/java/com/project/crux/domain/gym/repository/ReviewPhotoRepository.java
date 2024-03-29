package com.project.crux.domain.gym.repository;


import com.project.crux.domain.gym.domain.Review;
import com.project.crux.domain.gym.domain.ReviewPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Long> {
    List<ReviewPhoto> findAllByReview(Review review);
}
