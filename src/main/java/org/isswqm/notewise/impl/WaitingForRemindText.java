package org.isswqm.notewise.impl;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WaitingForRemindText implements Command{

    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if(NoteWise.reminderInfoList.isEmpty()){
            NoteWise.reminderInfoList.add(chatId);
            NoteWise.reminderInfoList.add(text);
        }

        message.setText("Введите дату и время напоминания. <2001-01-01 16:30>");
        NoteWise.statement = Statements.WAITING_FOR_REMIND_DATE_INPUT;
        return message;
    }
}
