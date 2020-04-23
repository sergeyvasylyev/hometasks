package com.vasylyev.hometasks.model.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TelegramMessage {
    @JsonProperty("message_id")
    private String messageId;
    private TelegramFrom from;
    private TelegramChat chat;
    private String date;
    private String text;
}
