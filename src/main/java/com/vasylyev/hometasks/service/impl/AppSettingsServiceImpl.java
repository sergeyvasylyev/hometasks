package com.vasylyev.hometasks.service.impl;

import com.vasylyev.hometasks.dto.AppSettingsDto;
import com.vasylyev.hometasks.exception.ElementNotFoundException;
import com.vasylyev.hometasks.mapper.AppSettingsMapper;
import com.vasylyev.hometasks.model.AppSettings;
import com.vasylyev.hometasks.model.enums.SettingType;
import com.vasylyev.hometasks.repository.AppSettingsRepository;
import com.vasylyev.hometasks.service.AppSettingsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AppSettingsServiceImpl implements AppSettingsService {

    private final AppSettingsMapper appSettingsMapper = new AppSettingsMapper();
    private final AppSettingsRepository appSettingsRepository;

    @Override
    public AppSettingsDto saveAppSetting(AppSettingsDto appSettingsDto) {
        AppSettingsDto appSettingsResult = appSettingsMapper.toDto(appSettingsRepository.save(appSettingsMapper.toModel(appSettingsDto)));
        log.info("App settings saved. Account: . Type: " + appSettingsDto.getSettingType());
        return appSettingsResult;
    }

    @Override
    public AppSettingsDto findById(Long id) {
        return appSettingsMapper.toDto(appSettingsRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("App setting not found. id: " + id)));
    }

    @Override
    public List<AppSettingsDto> findByAccountId(String id) {
        return appSettingsRepository.findByAccountId(id).stream()
                .map(a -> appSettingsMapper.toDto(a))
                .collect(Collectors.toList());
    }

    @Override
    public AppSettingsDto findByType(SettingType settingType) {
        return appSettingsMapper.toDto(appSettingsRepository.findBySettingType(settingType)
                .orElseThrow(() -> new ElementNotFoundException("App setting not found. Type: " + settingType)));
    }

    @Override
    public List<AppSettingsDto> findAll() {
        return appSettingsRepository.findAll().stream()
                .map(a -> appSettingsMapper.toDto(a))
                .collect(Collectors.toList());
    }

    @Override
    public String getSettingDataForDefaultAccount(SettingType settingType) {
        AppSettings appSettings = appSettingsRepository.findBySettingTypeDefaultAccount(settingType).orElse(null);
        if (Objects.nonNull(appSettings)) {
            return appSettings.getSettingData();
        }
        return "";
    }
}
