package com.vasylyev.hometasks.service;

import com.vasylyev.hometasks.model.Subscriber;

import java.util.List;

public interface SubscriberService {
    public List<Subscriber> findAll();

    public List<Subscriber> findAllActive();
}
