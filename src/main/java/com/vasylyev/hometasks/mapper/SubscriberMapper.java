package com.vasylyev.hometasks.mapper;

import com.vasylyev.hometasks.dto.SubscriberDto;
import com.vasylyev.hometasks.model.Subscriber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SubscriberMapper {

    private final AccountMapper accountMapper;

    public Subscriber toModel(SubscriberDto subscriberDto) {
        return Subscriber.builder()
                .active(subscriberDto.getActive())
                .name(subscriberDto.getName())
                .description(subscriberDto.getDescription())
                .chatId(subscriberDto.getChatId())
                .id(subscriberDto.getId())
                .accounts(subscriberDto.getAccounts().stream()
                        .map(a -> accountMapper.toModel(a))
                        .collect(Collectors.toList()))
                .build();
    }

    public SubscriberDto toDto(Subscriber subscriber) {
        return SubscriberDto.builder()
                .active(subscriber.getActive())
                .name(subscriber.getName())
                .description(subscriber.getDescription())
                .chatId(subscriber.getChatId())
                .id(subscriber.getId())
                .accounts(subscriber.getAccounts().stream()
                        .map(a -> accountMapper.toSimpleDto(a))
                        .collect(Collectors.toList()))
                .build();
    }
}
