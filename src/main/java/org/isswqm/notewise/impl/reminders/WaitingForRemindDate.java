package org.isswqm.notewise.impl.reminders;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
public class WaitingForRemindDate implements Command {
    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (!NoteWise.checkButtonPressAndSetStatement(text)){
            message.setText("Please select the function again");
        } else {
            NoteWise.reminderInfoMap.put("Date", text);
            message.setText("Save the reminder? <yes>");
            NoteWise.statement = Statements.REMIND_SAVING;
        }
        return message;
    }
}
