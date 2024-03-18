package com.project.crux.domain.crew.domain;

import com.project.crux.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CrewPhoto extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private CrewPost crewPost;

    public CrewPhoto(CrewPost crewPost, String imgUrl) {
        crewPost.getPhotoList().add(this);
        this.crewPost = crewPost;
        this.imgUrl = imgUrl;
    }
}
