package org.isswqm.notewise.impl.reminders;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WaitingForRemindText implements Command {

    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if(NoteWise.reminderInfoMap.isEmpty()){
            NoteWise.reminderInfoMap.put("ChatId", chatId);
            NoteWise.reminderInfoMap.put("Text", text);
        }

        message.setText("Enter the date and time of the reminder. <2001-01-01 16:30>");
        NoteWise.statement = Statements.WAITING_FOR_ADD_REMIND_DATE_INPUT;
        return message;
    }
}
