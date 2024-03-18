package com.project.crux.domain.crew.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrewRequestDto {
    private String name;
    private String content;
    private String imgUrl;
    private String mainActivityGym;
    private String mainActivityArea;
    private List<String> keywords;
}
