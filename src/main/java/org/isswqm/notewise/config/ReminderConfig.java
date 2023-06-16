package org.isswqm.notewise.config;

import java.time.LocalDateTime;

public class ReminderConfig {
    private final Long chatId;
    private final String message;

    private LocalDateTime datetime;

    public ReminderConfig(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDatetime() {
        return LocalDateTime.now();
    }
}
