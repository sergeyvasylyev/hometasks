package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.dto.SubscriberDto;
import com.vasylyev.hometasks.model.Subscriber;
import com.vasylyev.hometasks.model.telegram.TelegramChat;
import com.vasylyev.hometasks.service.SubscriberService;
import com.vasylyev.hometasks.telegram.TelegramNotifier;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final TelegramNotifier telegramNotifier;

    @RequestMapping(value = "/subscriber", method = RequestMethod.GET)
    public List<SubscriberDto> getAllSubscribers() {
        return subscriberService.findAll();
    }

    @RequestMapping(value = "/subscriberUpdates", method = RequestMethod.GET)
    public List<TelegramChat> getSubscriberUpdates() {
        return telegramNotifier.getChatList(telegramNotifier.getUpdates());
    }

}
