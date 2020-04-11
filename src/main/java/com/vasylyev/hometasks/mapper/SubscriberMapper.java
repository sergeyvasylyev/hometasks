package com.vasylyev.hometasks.mapper;

import com.vasylyev.hometasks.dto.SubscriberDto;
import com.vasylyev.hometasks.model.Subscriber;
import org.springframework.stereotype.Component;

@Component
public class SubscriberMapper {

    public Subscriber toModel(SubscriberDto subscriberDto){
        return Subscriber.builder()
                .active(subscriberDto.getActive())
                .name(subscriberDto.getName())
                .description(subscriberDto.getDescription())
                .chatId(subscriberDto.getChatId())
                .id(subscriberDto.getId())
                .build();
    }

    public SubscriberDto toDto(Subscriber subscriber){
        return SubscriberDto.builder()
                .active(subscriber.getActive())
                .name(subscriber.getName())
                .description(subscriber.getDescription())
                .chatId(subscriber.getChatId())
                .id(subscriber.getId())
                .build();
    }
}
