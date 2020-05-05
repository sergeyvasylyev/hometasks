package com.vasylyev.hometasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String name;
    private Boolean isDefault;
    private List<AppSettingsDto> appSettings;
    private Boolean active;
}
