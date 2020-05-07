package com.vasylyev.hometasks.telegram;

import com.vasylyev.hometasks.dto.AccountSimpleDto;
import com.vasylyev.hometasks.dto.SubscriberDto;
import com.vasylyev.hometasks.service.AccountService;
import com.vasylyev.hometasks.service.CourseService;
import com.vasylyev.hometasks.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AccountService accountService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message updateMessage = update.getMessage();
            List<AccountSimpleDto> accountSimpleDtoList = accountService.findAllActiveSimple();
            createSubscriberIfNotExist(updateMessage, accountSimpleDtoList);
            switch (updateMessage.getText()) {
                case "task":
                    sendMessage(updateMessage.getChatId().toString(), updateMessage.getText());
                    break;
                case "accounts":
                    sendMessage(updateMessage.getChatId().toString(),
                            accountSimpleDtoList.stream().map(a -> a.getName()).collect(Collectors.toList()).toString());
                    break;
                case "Егор":
                    sendMessage(updateMessage.getChatId().toString(), "Привет, Егор Дио!");
                    break;
                case "подписчики":
                    sendMessage(updateMessage.getChatId().toString(),
                            subscriberService.findAll().toString());
                    break;
                case "детали":
                    sendMessage(updateMessage.getChatId().toString(),
                            subscriberService.findByChatId(updateMessage.getChatId().toString()).toString());
                    break;
                case "классы":
                    sendMessage(updateMessage.getChatId().toString(),
                            courseService.findAllNames().toString());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    private void createSubscriberIfNotExist(Message updateMessage, List<AccountSimpleDto> accountSimpleDtoList) {
        SubscriberDto subscriber = subscriberService.findByChatId(updateMessage.getChatId().toString());
        if (isNull(subscriber)) {
            log.info("New subscriber. Chat id: " + updateMessage.getChatId().toString());
            subscriberService.save(SubscriberDto.builder()
                    .chatId(updateMessage.getChatId().toString())
                    .description(updateMessage.getFrom().getUserName())
                    .name(updateMessage.getFrom().getUserName())
                    .active(true)
                    .build());
        } else {
            AccountSimpleDto accountToAdd = accountSimpleDtoList.stream()
                    .filter(a -> a.getName().equals(updateMessage.getText())).findFirst().orElse(null);
            if (nonNull(accountToAdd)) {
                List<AccountSimpleDto> subscriberAccounts = subscriber.getAccounts();
                if (subscriberAccounts.stream()
                        .filter(sa -> sa.equals(accountToAdd)).findFirst().orElse(null) == null) {
                    subscriberAccounts.add(accountToAdd);
                    subscriber.setAccounts(subscriberAccounts);
                    subscriberService.save(subscriber);
                }
            }
        }
    }

    private void sendMessage(String chatId, String msg) {
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(msg);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending telegram message. " + e.getMessage());
        }
    }
}
