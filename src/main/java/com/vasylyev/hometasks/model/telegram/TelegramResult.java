package com.vasylyev.hometasks.model.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TelegramResult {
    @JsonProperty("update_id")
    private String updateId;
    private TelegramMessage message;
}
