package org.isswqm.notewise.config;

import java.time.LocalDateTime;

public class NoteConfig {
    private final Long chatId;
    private final String message;
    //private final LocalDateTime dateTime;

    public NoteConfig(Long chatId, String message){
        this.chatId = chatId;
        this.message = message;
        //this.dateTime = dateTime;
    }

    public Long getChatId(){
        return chatId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime(){
        return LocalDateTime.now();
    }
}
