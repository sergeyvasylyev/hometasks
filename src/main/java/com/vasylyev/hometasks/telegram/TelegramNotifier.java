package com.vasylyev.hometasks.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasylyev.hometasks.dto.SubscriberDto;
import com.vasylyev.hometasks.model.Subscriber;
import com.vasylyev.hometasks.model.telegram.TelegramBotUpdate;
import com.vasylyev.hometasks.model.telegram.TelegramChat;
import com.vasylyev.hometasks.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramNotifier {

    private final SubscriberService subscriberService;
    private final ObjectMapper objectMapper;
    private final TelegramBot telegramBot;

    public void sendToTelegram(String message) {
        List<SubscriberDto> subscriberList = subscriberService.findAllActive();
        for (SubscriberDto subscriber : subscriberList) {
            SendMessage sm = new SendMessage();
            sm.setText(message)
                    .setChatId(subscriber.getChatId());
            try {
                telegramBot.execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            //sendWithHTTP(subscriber, message);
        }
    }

    public TelegramBotUpdate getUpdates() {
        TelegramBotUpdate telegramBotUpdate = null;
        try {
            telegramBotUpdate = objectMapper.readValue(getUpdatesByHTTP(), TelegramBotUpdate.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing telegram getUpdates response! " + e.getMessage());
        }

        return telegramBotUpdate;
    }

    public List<TelegramChat> getChatList(TelegramBotUpdate telegramBotUpdate) {
        List<TelegramChat> chatList = new ArrayList<>();
        telegramBotUpdate.getResult().stream().forEach(r -> chatList.add(r.getMessage().getChat()));
        return chatList;
    }

    private String getUpdatesByHTTP() {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{token}/getUpdates");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + telegramBot.getBotToken()))
                .timeout(Duration.ofSeconds(5))
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private void sendWithHTTP(Subscriber subscriber, String message) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{token}/sendMessage")
                .queryParam("chat_id", subscriber.getChatId())
                .queryParam("text", message);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + telegramBot.getBotToken()))
                .timeout(Duration.ofSeconds(5))
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Telegram message sent. chatId:" + subscriber.getChatId() + " apiToken:" + telegramBot.getBotToken());
        } catch (IOException e) {
            log.error(e.getMessage());
            log.error("Error. Telegram message not sent. chatId:" + subscriber.getChatId() + " apiToken:" + telegramBot.getBotToken());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            log.error("Error. Telegram message not sent. chatId:" + subscriber.getChatId() + " apiToken:" + telegramBot.getBotToken());
        }
    }
}
