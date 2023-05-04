package org.isswqm.notewise.handlers;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.ReminderConfig;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReminderHandler {

    public void remind(String chatId, String message, String datetimeString) throws SQLException {

        Remind remind = new Remind();

        LocalDateTime datetime = LocalDateTime.parse(datetimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        ReminderConfig reminder = new ReminderConfig(1L, message, datetime);

        remind.saveRemind(reminder);

    }

}

