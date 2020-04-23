package com.vasylyev.hometasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AccountDto {
    private String name;
    private Boolean isDefault;
    private List<AppSettingsDto> appSettings;
}
