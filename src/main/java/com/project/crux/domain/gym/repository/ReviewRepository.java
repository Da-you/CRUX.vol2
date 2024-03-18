package com.project.crux.domain.gym.repository;


import com.project.crux.domain.gym.domain.Gym;
import com.project.crux.domain.gym.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByGym(Gym gym);
    List<Review> findByGymOrderByIdDesc(Gym gym);

}
