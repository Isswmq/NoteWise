package org.isswqm.notewise.handlers;

import org.isswqm.notewise.command.RemindCommand;
import org.isswqm.notewise.config.ReminderConfig;

import java.sql.SQLException;
public class ReminderHandler {
    public void remind(String chatId, String message) throws SQLException {

        RemindCommand remindCommand = new RemindCommand();

        ReminderConfig reminderConfig = new ReminderConfig(Long.valueOf(chatId), message);

        remindCommand.saveRemind(reminderConfig);
    }
}

