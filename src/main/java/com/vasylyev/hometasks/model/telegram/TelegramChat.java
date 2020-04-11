package com.vasylyev.hometasks.model.telegram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TelegramChat {
    private String id;
    private String first_name;
    private String last_name;
    private String username;
    private String type;
}
