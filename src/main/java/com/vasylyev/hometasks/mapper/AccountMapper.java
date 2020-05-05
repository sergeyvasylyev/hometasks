package com.vasylyev.hometasks.mapper;

import com.vasylyev.hometasks.dto.AccountDto;
import com.vasylyev.hometasks.dto.AccountSimpleDto;
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
                .active(accountDto.getActive())
                .build();
    }

    public Account toModel(AccountSimpleDto accountSimpleDto) {
        return Account.builder()
                .name(accountSimpleDto.getName())
                .isDefault(accountSimpleDto.getIsDefault())
                .active(accountSimpleDto.getActive())
                .build();
    }

    public AccountDto toDto(Account account) {
        return AccountDto.builder()
                .name(account.getName())
                .isDefault(account.getIsDefault())
                .appSettings(account.getAppSettings().stream()
                        .map(appSettings -> appSettingsMapper.toDto(appSettings))
                        .collect(Collectors.toList()))
                .active(account.getActive())
                .build();
    }

    public AccountSimpleDto toSimpleDto(Account account) {
        return AccountSimpleDto.builder()
                .name(account.getName())
                .isDefault(account.getIsDefault())
                .active(account.getActive())
                .build();
    }
}
