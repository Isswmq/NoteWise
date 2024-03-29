package org.isswqm.notewise.button;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class DeleteRemindButton implements Button{
    @Override
    public SendMessage execute(String chatId, String text) {
        NoteWise.statement = Statements.WAITING_FOR_DELETE_REMIND;
        return new SendMessage(chatId, "Enter the date of the reminder for deletion, <2001-01-01 13:37>");
    }
}
