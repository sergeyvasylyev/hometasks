package com.vasylyev.hometasks.telegram;

import com.vasylyev.hometasks.dto.SubscriberDto;
import com.vasylyev.hometasks.service.CourseService;
import com.vasylyev.hometasks.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static java.util.Objects.isNull;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private CourseService courseService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message updateMessage = update.getMessage();
            createSubscriberIfNotExist(updateMessage);
            switch (updateMessage.getText()) {
                case "task":
                    sendMessage(updateMessage.getChatId().toString(), updateMessage.getText());
                    break;
                case "Егор":
                    sendMessage(updateMessage.getChatId().toString(), "Привет, Егор Дио!");
                    break;
                case "подписчики":
                    sendMessage(updateMessage.getChatId().toString()
                            , subscriberService.findAll().toString());
                    break;
                case "детали":
                    sendMessage(updateMessage.getChatId().toString()
                            , subscriberService.findByChatId(updateMessage.getChatId().toString()).toString());
                    break;
                case "классы":
                    sendMessage(updateMessage.getChatId().toString()
                            , courseService.findAllNames().toString());
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

    private void createSubscriberIfNotExist(Message updateMessage) {
        SubscriberDto subscriber = subscriberService.findByChatId(updateMessage.getChatId().toString());
        if (isNull(subscriber)) {
            subscriberService.save(SubscriberDto.builder()
                    .chatId(updateMessage.getChatId().toString())
                    .description(updateMessage.getFrom().getUserName())
                    .name(updateMessage.getFrom().getUserName())
                    .active(true)
                    .build());
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
