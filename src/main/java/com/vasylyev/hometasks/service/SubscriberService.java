package com.vasylyev.hometasks.service;

import com.vasylyev.hometasks.dto.SubscriberDto;

import java.util.List;

public interface SubscriberService {

    List<SubscriberDto> findAll();

    List<SubscriberDto> findAllActive();

    List<SubscriberDto> findAllActiveByAccountId(String accountId);

    SubscriberDto findById(String id);

    SubscriberDto findByChatId(String chatId);

    SubscriberDto save(SubscriberDto subscriberDto);
}
