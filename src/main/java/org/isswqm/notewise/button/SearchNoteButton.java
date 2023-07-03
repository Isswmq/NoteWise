package org.isswqm.notewise.button;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SearchNoteButton implements Button{
    @Override
    public SendMessage execute(String chatId, String text) {
        NoteWise.statement = Statements.WAITING_FOR_SEARCH_NOTE_DATE_INPUT;
        return new SendMessage(chatId, "Введите дату заметки для просмотра. <2001-01-01>");
    }
}
