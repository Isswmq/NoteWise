package org.isswqm.notewise.impl.searchnote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WaitingForSearchNoteDateInput implements Command {

    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (!NoteWise.checkButtonPressAndSetStatement(text)){
            message.setText("Please select the function again");
        }else {
            NoteWise.searchNoteMap.put("Text", text);
            NoteWise.statement = Statements.NOTE_FOUND;
            message.setText("Would you like to view the notes? <yes/no>");
        }
        return message;
    }
}
