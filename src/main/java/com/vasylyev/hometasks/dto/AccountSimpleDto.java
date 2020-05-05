package com.vasylyev.hometasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountSimpleDto {
    private String name;
    private Boolean isDefault;
    private Boolean active;
}