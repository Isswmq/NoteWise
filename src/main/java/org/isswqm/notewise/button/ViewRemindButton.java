package org.isswqm.notewise.button;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ViewRemindButton implements Button{
    @Override
    public SendMessage execute(String chatId, String text) {
        NoteWise.statement = Statements.WAITING_FOR_VIEW_REMIND;
        return new SendMessage(chatId, "Хотите просмотреть напоминания? <да/нет>");
    }
}
