package com.vasylyev.hometasks.telegram;

import com.vasylyev.hometasks.model.Subscriber;
import com.vasylyev.hometasks.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramNotifier {

    @Value("${telegram.bot.name}")
    private String channelName;

    @Value("${telegram.bot.token}")
    private String apiToken;

    private final SubscriberService subscriberService;

    public void sendToTelegram(String message) {

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        List<Subscriber> subscriberList = subscriberService.findAllActive();

        for (Subscriber subscriber : subscriberList) {
            UriBuilder builder = UriBuilder
                    .fromUri("https://api.telegram.org")
                    .path("/{token}/sendMessage")
                    .queryParam("chat_id", subscriber.getChatId())
                    .queryParam("text", message);

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(builder.build("bot" + apiToken))
                    .timeout(Duration.ofSeconds(5))
                    .build();

            try {
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                log.info("Telegram message sent. chatId:" + subscriber.getChatId() + " apiToken:" + apiToken);
            } catch (IOException e) {
                log.error(e.getMessage());
                log.error("Error. Telegram message not sent. chatId:" + subscriber.getChatId() + " apiToken:" + apiToken);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                log.error("Error. Telegram message not sent. chatId:" + subscriber.getChatId() + " apiToken:" + apiToken);
            }
        }

    }
}
