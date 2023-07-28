package org.isswqm.notewise.impl.addnote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WaitingForNoteTextInput implements Command {
    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (!NoteWise.checkButtonPressAndSetStatement(text)){
            message.setText("Please select the function again");
        }else {
            NoteWise.noteInfoMap.put("ChatId", chatId);
            NoteWise.noteInfoMap.put("Text", text);
            NoteWise.statement = Statements.NOTE_SAVING;
            message.setText("Save the note <yes>");
        }
        return message;
    }
}
