package org.isswqm.notewise.button;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class RemindersButton implements Button{
    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, "Enter the text of the reminder");
        NoteWise.statement = Statements.WAITING_FOR_ADD_REMIND_TEXT_INPUT;
        return message;
    }
}
