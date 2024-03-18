package com.project.crux.domain.gym.repository;

import com.project.crux.domain.gym.domain.Gym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface GymRepositoryCustom {

    Page<Gym> findByCustomCursor(double customCursor, PageRequest pageRequest);
}
