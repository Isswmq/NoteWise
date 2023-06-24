package org.isswqm.notewise.button;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class AddNoteButton implements Button{
    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, "Введите текст заметки");
        NoteWise.statement = Statements.WAITING_FOR_NOTE_TEXT_INPUT;
        return message;
    }
}
