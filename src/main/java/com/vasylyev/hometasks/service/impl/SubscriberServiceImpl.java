package com.vasylyev.hometasks.service.impl;

import com.vasylyev.hometasks.dto.AccountSimpleDto;
import com.vasylyev.hometasks.dto.SubscriberDto;
import com.vasylyev.hometasks.exception.ElementNotFoundException;
import com.vasylyev.hometasks.mapper.AccountMapper;
import com.vasylyev.hometasks.mapper.SubscriberMapper;
import com.vasylyev.hometasks.model.Account;
import com.vasylyev.hometasks.model.Subscriber;
import com.vasylyev.hometasks.repository.SubscriberRepository;
import com.vasylyev.hometasks.service.AccountService;
import com.vasylyev.hometasks.service.SubscriberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;


@Slf4j
@Service
@AllArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberMapper subscriberMapper;
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @Override
    public List<SubscriberDto> findAll() {
        return subscriberRepository.findAll().stream()
                .map(s -> subscriberMapper.toDto(s))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriberDto> findAllActive() {
        return subscriberRepository.findByActiveTrue().stream()
                .map(s -> subscriberMapper.toDto(s))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriberDto> findAllActiveByAccountId(String accountId) {
        return subscriberRepository.findByAccountIdByActiveTrue(accountId).stream()
                .map(s -> subscriberMapper.toDto(s))
                .collect(Collectors.toList());
    }

    @Override
    public SubscriberDto findById(String id) {
        return subscriberMapper.toDto(
                subscriberRepository.findById(Long.valueOf(id)).orElseThrow(() -> new ElementNotFoundException("Subscriber not found. id: " + id)));
    }

    @Override
    public SubscriberDto findByChatId(String chatId) {
        Subscriber subscriber = subscriberRepository.findByChatId(chatId).orElse(null);
        return nonNull(subscriber) ? subscriberMapper.toDto(subscriber) : null;
    }

    @Override
    public SubscriberDto save(SubscriberDto subscriberDto) {
        Subscriber subscriber = subscriberMapper.toModel(subscriberDto);
        List<Account> accounts = new ArrayList<>();
        for (AccountSimpleDto accountSimpleDto : subscriberDto.getAccounts()) {
            Account account = accountMapper.toModel(accountService.findByName(accountSimpleDto.getName()));
            account.getAppSettings().forEach(a -> a.setAccount(account));
            accounts.add(account);
        }
        subscriber.setAccounts(accounts);
        Subscriber result = subscriberRepository.save(subscriber);
        log.info("Subscriber saved. id: " + result.getId() + ". name: " + result.getName());
        return subscriberMapper.toDto(result);
    }
}
