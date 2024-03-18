package com.project.crux.domain.gym.repository;


import com.project.crux.domain.gym.domain.Gym;
import com.project.crux.domain.gym.domain.LikeGym;
import com.project.crux.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeGymRepository extends JpaRepository<LikeGym, Long> {
    Optional<LikeGym> findByMemberAndGym(Member member, Gym gym);
    List<LikeGym> findAllByMember(Member member);
    Long countLikeGymByGym(Gym gym);
}
