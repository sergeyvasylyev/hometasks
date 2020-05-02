package com.vasylyev.hometasks.service;

import com.vasylyev.hometasks.dto.AppSettingsDto;
import com.vasylyev.hometasks.model.enums.SettingType;

import java.util.List;

public interface AppSettingsService {

    AppSettingsDto saveAppSetting(AppSettingsDto appSettingsDto);

    AppSettingsDto findById(Long id);

    List<AppSettingsDto> findByAccountId(String id);

    AppSettingsDto findByType(SettingType settingType);

    String getSettingDataForDefaultAccount(SettingType settingType);

    List<AppSettingsDto> findAll();

}
