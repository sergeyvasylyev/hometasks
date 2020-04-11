package com.vasylyev.hometasks.model.telegram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TelegramBotUpdate {
    private String ok;
    private List<TelegramResult> result;
}
