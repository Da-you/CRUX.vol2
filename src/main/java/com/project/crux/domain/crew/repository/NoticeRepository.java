package com.project.crux.domain.crew.repository;

import com.project.crux.domain.crew.domain.Crew;
import com.project.crux.domain.crew.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
    List<Notice> findAllByCrewMember_Crew(Crew crew);
}
