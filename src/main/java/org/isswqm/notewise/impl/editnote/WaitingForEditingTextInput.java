package org.isswqm.notewise.impl.editnote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WaitingForEditingTextInput implements Command {

    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (!NoteWise.checkButtonPressAndSetStatement(text)){
            message.setText("Please select the function again");
        } else {
            NoteWise.editNoteMap.put("Text", text);
            message.setText("Would you like to modify the note? <yes>");
            NoteWise.statement = Statements.NOTE_EDITED;
        }
        return message;
    }
}
