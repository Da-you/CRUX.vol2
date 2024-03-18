package com.project.crux.domain.crew.domain;

import com.project.crux.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class CrewPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "crewPost", cascade = CascadeType.REMOVE)
    private List<CrewPhoto> photoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private CrewMember crewMember;

    public CrewPost(CrewMember crewMember) {
        this.crewMember = crewMember;
    }
}
