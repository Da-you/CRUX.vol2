package com.project.crux.domain.crew.dto.response;

import com.project.crux.domain.crew.domain.CrewPhoto;
import lombok.Getter;

@Getter
public class CrewPhotoResponseDto {
    private Long photoId;
    private String imgUrl;

    public CrewPhotoResponseDto(CrewPhoto photo) {
        this.photoId = photo.getId();
        this.imgUrl = photo.getImgUrl();
    }
}
