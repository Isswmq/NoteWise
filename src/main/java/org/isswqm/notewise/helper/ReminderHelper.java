package org.isswqm.notewise.helper;

import org.isswqm.notewise.command.RemindCommand;
import org.isswqm.notewise.config.ReminderConfig;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReminderHelper {
    public void remind(ArrayList<String> info) throws SQLException {
        try {
            RemindCommand remindCommand = new RemindCommand();

            Long chatId = Long.valueOf(info.get(0));

            String message = info.get(1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            LocalDateTime date = LocalDateTime.parse(info.get(2), formatter);

            ReminderConfig reminderConfig = new ReminderConfig(chatId, message, date);

            remindCommand.saveRemind(reminderConfig);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

