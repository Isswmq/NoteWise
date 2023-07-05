package org.isswqm.notewise.button;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class EditNoteButton implements Button{
    @Override
    public SendMessage execute(String chatId, String text) {
        NoteWise.statement = Statements.WAITING_FOR_EDITING_NOTE_DATE_INPUT;
        return new SendMessage(chatId, "Enter the date of the note for modification, <2001-01-01>");
    }
}
