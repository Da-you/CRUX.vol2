package com.project.crux.global.sse.domain.dto;

import com.project.crux.global.sse.domain.NotificationContent;
import com.project.crux.global.sse.domain.Notification;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponseDto {
    private Long id;
    private NotificationContent content;
    private Boolean status;

    public static NotificationResponseDto from(Notification notification) {

        return NotificationResponseDto.builder()
                .id(notification.getId())
                .content(notification.getNotificationContent())
                .status(notification.getIsRead())
                .build();
    }
}
