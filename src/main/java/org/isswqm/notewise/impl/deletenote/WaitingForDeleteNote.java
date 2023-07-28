package org.isswqm.notewise.impl.deletenote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WaitingForDeleteNote implements Command {

    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (!NoteWise.checkButtonPressAndSetStatement(text)){
            message.setText("Please select the function again");
        } else {
            NoteWise.deleteNoteMap.put("Text", text);
            NoteWise.statement = Statements.NOTE_DELETED;
            message.setText("Delete the note? <yes>");
        }
        return message;
    }
}
