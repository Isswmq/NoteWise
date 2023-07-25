package org.isswqm.notewise.impl.deletenote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WaitingForDeleteNote implements Command {

    @Override
    public SendMessage execute(String chatId, String text) {
        NoteWise.deleteNoteMap.put("Text", text);
        SendMessage message = new SendMessage(chatId, "Delete the note? <yes/no>");
        NoteWise.statement = Statements.NOTE_DELETED;
        return message;
    }
}
