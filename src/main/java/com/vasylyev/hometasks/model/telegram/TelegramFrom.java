package com.vasylyev.hometasks.model.telegram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TelegramFrom {
    private String id;
    private boolean is_bot;
    private String first_name;
    private String last_name;
    private String username;
    private String language_code;
}
