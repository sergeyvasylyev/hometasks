package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.dto.SubscriberDto;
import com.vasylyev.hometasks.model.telegram.TelegramChat;
import com.vasylyev.hometasks.service.SubscriberService;
import com.vasylyev.hometasks.telegram.TelegramNotifier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/subscriber")
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final TelegramNotifier telegramNotifier;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView subscriber() {
        ModelAndView modelSubscriber = new ModelAndView();
        modelSubscriber.setViewName("subscriber");
        return modelSubscriber;
    }

    @RequestMapping(method = RequestMethod.POST)
    public SubscriberDto addSubscriber() {
        return SubscriberDto.builder().build();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<SubscriberDto> getAllSubscribers() {
        return subscriberService.findAll();
    }

    @RequestMapping(value = "/{subscriberId}", method = RequestMethod.GET)
    public SubscriberDto getSubscriber(@PathVariable String subscriberId) {
        return subscriberService.findById(subscriberId);
    }

    @RequestMapping(value = "/{subscriberId}", method = RequestMethod.PUT)
    public SubscriberDto updateSubscriber(@PathVariable String subscriberId, @ModelAttribute("subscriberDto") SubscriberDto subscriberDto) {
        return subscriberService.save(subscriberDto);
    }

    @RequestMapping(value = "/subscriberUpdates", method = RequestMethod.GET)
    public List<TelegramChat> getSubscriberUpdates() {
        return telegramNotifier.getChatList(telegramNotifier.getUpdates());
    }

}