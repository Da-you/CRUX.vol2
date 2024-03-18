package com.project.crux.domain.crew.repository;

import com.project.crux.domain.crew.domain.Crew;
import com.project.crux.domain.crew.domain.LikeCrew;
import com.project.crux.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeCrewRepository extends JpaRepository<LikeCrew, Long> {
    Optional<LikeCrew> findByCrewAndMember(Crew crew, Member member);
    List<LikeCrew> findAllByMember(Member member);
}
