package org.isswqm.notewise.impl.addnote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WaitingForNoteText implements Command {

    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, "Сохранить заметку <да/нет>");
        NoteWise.noteInfoList.add(chatId);
        NoteWise.noteInfoList.add(text);
        NoteWise.statement = Statements.NOTE_IS_SAVING;
        return message;
    }
}
