package org.isswqm.notewise.impl;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
public class WaitingForRemindDate implements Command{
    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        NoteWise.reminderInfoList.add(text);

        message.setText("сохранить напоминание? <да/нет>");
        NoteWise.statement = Statements.REMIND_IS_SAVING;
        return message;
    }
}
