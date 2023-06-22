package org.isswqm.notewise.config;

import java.time.LocalDateTime;

public class ReminderConfig {
    private final Long chatId;
    private final String message;

    private LocalDateTime datetime;

    public ReminderConfig(Long chatId, String message, LocalDateTime datetime) {
        this.chatId = chatId;
        this.message = message;
        this.datetime = datetime;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
