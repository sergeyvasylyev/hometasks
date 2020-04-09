package com.vasylyev.hometasks.service.impl;

import com.vasylyev.hometasks.model.Subscriber;
import com.vasylyev.hometasks.repository.SubscriberRepository;
import com.vasylyev.hometasks.service.SubscriberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Override
    public List<Subscriber> findAll() {
        return subscriberRepository.findAll();
    }

    @Override
    public List<Subscriber> findAllActive() {
        return subscriberRepository.findByActiveTrue();
    }
}
