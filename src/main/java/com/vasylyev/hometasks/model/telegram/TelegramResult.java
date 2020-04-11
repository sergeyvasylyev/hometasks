package com.vasylyev.hometasks.model.telegram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TelegramResult {
    private String update_id;
    private TelegramMessage message;
}
