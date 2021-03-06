package com.vasylyev.hometasks.service.impl;

import com.vasylyev.hometasks.dto.AccountDto;
import com.vasylyev.hometasks.dto.AccountSimpleDto;
import com.vasylyev.hometasks.exception.ElementNotFoundException;
import com.vasylyev.hometasks.mapper.AccountMapper;
import com.vasylyev.hometasks.model.Account;
import com.vasylyev.hometasks.repository.AccountRepository;
import com.vasylyev.hometasks.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    @Override
    public void addAccount(AccountDto accountDto) {
        Account existingAccount = accountRepository.findById(accountDto.getName()).orElse(null);
        if (isNull(existingAccount)) {
            if (isNull(accountDto.getAppSettings())) {
                accountDto.setAppSettings(new ArrayList<>());
            }
        } else {
            if (isNull(accountDto.getAppSettings())) {
                accountDto.setAppSettings(accountMapper.toDto(existingAccount).getAppSettings());
            }
        }
        Account account = accountMapper.toModel(accountDto);
        account.getAppSettings().forEach(a -> a.setAccount(account));
        accountRepository.save(account);
        log.info("Account saved: " + accountDto.getName());
    }

    @Override
    public AccountDto findByName(String name) {
        return accountMapper.toDto(accountRepository.findById(name)
                .orElseThrow(() -> new ElementNotFoundException("Account not found. name:" + name)));
    }

    @Override
    @Transactional
    public AccountDto getDefaultAccount() {
        return accountMapper.toDto(accountRepository.findByIsDefault(true)
                .orElseThrow(() -> new ElementNotFoundException("Account not found. Default: true")));
    }

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream()
                .map(a -> accountMapper.toDto(a)).collect(Collectors.toList());
    }

    @Override
    public List<AccountSimpleDto> findAllSimple() {
        return accountRepository.findAll().stream()
                .map(a -> accountMapper.toSimpleDto(a))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<AccountDto> findAllActive() {
        return accountRepository.findByActiveTrue().stream()
                .map(a -> accountMapper.toDto(a))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountSimpleDto> findAllActiveSimple() {
        return accountRepository.findByActiveTrue().stream()
                .map(a -> accountMapper.toSimpleDto(a))
                .collect(Collectors.toList());
    }
}
