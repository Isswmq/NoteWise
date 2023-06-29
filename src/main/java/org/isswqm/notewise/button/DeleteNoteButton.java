package org.isswqm.notewise.button;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class DeleteNoteButton implements Button{

    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, "Введите дату заметки для удаления, <2001-01-01>");
        NoteWise.statement = Statements.WAITING_FOR_DELETE_NOTE;
        return message;
    }
}
