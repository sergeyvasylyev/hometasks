package com.vasylyev.hometasks.dto;

import com.vasylyev.hometasks.model.enums.SettingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AppSettingsDto {
    private Long id;
    private SettingType settingType;
    private String settingData;
    //@JsonManagedReference
    //private AccountDto account;
}
