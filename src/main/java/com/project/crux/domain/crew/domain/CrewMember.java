package com.project.crux.domain.crew.domain;

import com.project.crux.domain.crew.Status;
import com.project.crux.domain.member.domain.Member;
import com.project.crux.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class CrewMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "crew_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Crew crew;

    @OneToMany(mappedBy = "crewMember", cascade = CascadeType.REMOVE)
    private List<CrewPost> crewPostList = new ArrayList<>();

    @OneToMany(mappedBy = "crewMember", cascade = CascadeType.REMOVE)
    private List<Notice> noticeList = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.SUBMIT;

    public CrewMember(Member member, Crew crew) {
        this.member = member;
        this.crew = crew;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
