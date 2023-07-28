package org.isswqm.notewise.impl.editnote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WaitingForEditingDateInput implements Command {
    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (!NoteWise.checkButtonPressAndSetStatement(text)){
            message.setText("Please select the function again");
        } else {
            NoteWise.editNoteMap.put("Date", text);
            message.setText("Enter the new text of the note");
            NoteWise.statement = Statements.WAITING_FOR_EDITING_NOTE_TEXT_INPUT;
        }
        return message;
    }
}
