package com.vasylyev.hometasks.model.telegram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TelegramMessage {
    private String message_id;
    TelegramFrom from;
    TelegramChat chat;
    private String date;
    private String text;
}
