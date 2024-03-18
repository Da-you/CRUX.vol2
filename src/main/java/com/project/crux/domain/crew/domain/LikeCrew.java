package com.project.crux.domain.crew.domain;

import com.project.crux.domain.member.domain.Member;
import com.project.crux.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class LikeCrew extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Crew crew;

    public LikeCrew(Crew crew, Member member) {
        this.crew = crew;
        this.member = member;
    }
}
