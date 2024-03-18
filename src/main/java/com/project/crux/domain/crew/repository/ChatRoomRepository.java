package com.project.crux.domain.crew.repository;

import com.project.crux.domain.crew.domain.ChatRoom;
import com.project.crux.domain.crew.domain.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByCrew(Crew crew);
}
