package com.vasylyev.hometasks.mapper;

import com.vasylyev.hometasks.dto.AccountDto;
import com.vasylyev.hometasks.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final AppSettingsMapper appSettingsMapper = new AppSettingsMapper();

    public Account toModel(AccountDto accountDto) {
        return Account.builder()
                .name(accountDto.getName())
                .isDefault(accountDto.getIsDefault())
                .appSettings(accountDto.getAppSettings().stream()
                        .map(appSettingsDto -> appSettingsMapper.toModel(appSettingsDto))
                        .collect(Collectors.toList()))
                .build();
    }

    public AccountDto toDto(Account account) {
        return AccountDto.builder()
                .name(account.getName())
                .isDefault(account.getIsDefault())
                .appSettings(account.getAppSettings().stream()
                        .map(appSettings -> appSettingsMapper.toDto(appSettings))
                        .collect(Collectors.toList()))
                .build();
    }
}
