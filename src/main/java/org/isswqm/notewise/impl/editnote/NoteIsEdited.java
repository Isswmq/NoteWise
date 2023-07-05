package org.isswqm.notewise.impl.editnote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NoteIsEdited implements Command {

    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Something went wrong");
        String noteDate = NoteWise.editNoteList.get(0);
        String noteText = NoteWise.editNoteList.get(1);
        if(text.equalsIgnoreCase("yes")){
            try {
                Connection connection = DatabaseConnector.getConnection();
                String sql = "UPDATE notewise_db.public.notes SET message = ? WHERE to_char(note_date, 'YYYY-MM-DD') = ? AND chat_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, noteText);
                statement.setString(2, noteDate);
                statement.setLong(3, Long.parseLong(chatId));
                statement.executeUpdate();
                message.setText("Note modified");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else {
            message.setText("Cancellation of deletion");
        }

        NoteWise.statement = Statements.WAITING;
        return message;
    }
}
