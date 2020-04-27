package com.vasylyev.hometasks.service.impl;

import com.vasylyev.hometasks.dto.AccountDto;
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
        if (isNull(accountDto.getAppSettings())) {
            accountDto.setAppSettings(new ArrayList<>());
        }
        Account account = accountMapper.toModel(accountDto);
        account.getAppSettings().stream().forEach(a -> a.setAccount(account));
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
}
