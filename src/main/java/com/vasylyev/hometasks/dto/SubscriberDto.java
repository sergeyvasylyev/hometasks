package com.vasylyev.hometasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberDto {
    private Long id;
    private String name;
    private String description;
    private String chatId;
    private Boolean active;
    private List<AccountSimpleDto> accounts;
}
