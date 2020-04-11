package com.vasylyev.hometasks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriberDto {
    private Long id;
    private String name;
    private String description;
    private String chatId;
    private Boolean active;
}
