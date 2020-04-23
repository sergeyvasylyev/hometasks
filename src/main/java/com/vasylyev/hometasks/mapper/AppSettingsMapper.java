package com.vasylyev.hometasks.mapper;

import com.vasylyev.hometasks.dto.AppSettingsDto;
import com.vasylyev.hometasks.model.AppSettings;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
@AllArgsConstructor
@NoArgsConstructor
public class AppSettingsMapper {

    @Autowired
    private AccountMapper accountMapper;

    public AppSettings toModel(AppSettingsDto appSettingsDto) {
        return AppSettings.builder()
                .id(appSettingsDto.getId())
                .settingType(appSettingsDto.getSettingType())
                .settingData(appSettingsDto.getSettingData())
                //.account(accountMapper.toModel(appSettingsDto.getAccount()))
                .build();
    }

    public AppSettingsDto toDto(AppSettings appSettings) {
        return AppSettingsDto.builder()
                .id(appSettings.getId())
                .settingType(appSettings.getSettingType())
                .settingData(appSettings.getSettingData())
                //.account(accountMapper.toDto(appSettings.getAccount()))
                .build();
    }
}
