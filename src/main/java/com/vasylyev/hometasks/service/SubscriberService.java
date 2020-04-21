package com.vasylyev.hometasks.service;

import com.vasylyev.hometasks.dto.SubscriberDto;

import java.util.List;

public interface SubscriberService {

    public List<SubscriberDto> findAll();

    public List<SubscriberDto> findAllActive();

    public SubscriberDto findById(String id);

    public SubscriberDto findByChatId(String chatId);

    public SubscriberDto save(SubscriberDto subscriberDto);
}
