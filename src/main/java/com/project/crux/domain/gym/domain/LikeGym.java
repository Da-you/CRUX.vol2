package com.project.crux.domain.gym.domain;

import com.project.crux.domain.member.domain.Member;
import com.project.crux.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LikeGym extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "gym_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Gym gym;

    public LikeGym(Member member, Gym gym) {
        this.member = member;
        this.gym = gym;
    }

}
