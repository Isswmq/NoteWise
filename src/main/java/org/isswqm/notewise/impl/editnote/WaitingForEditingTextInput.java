package org.isswqm.notewise.impl.editnote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WaitingForEditingTextInput implements Command {

    @Override
    public SendMessage execute(String chatId, String text) {
        NoteWise.editNoteList.add(text);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Хотите изменить заметку? <да/нет>");
        NoteWise.statement = Statements.NOTE_IS_EDITED;
        return message;
    }
}
