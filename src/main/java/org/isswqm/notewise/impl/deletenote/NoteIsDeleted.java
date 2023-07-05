package org.isswqm.notewise.impl.deletenote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NoteIsDeleted implements Command {
    @Override
    public SendMessage execute(String chatId, String text)  {
        String date = NoteWise.deleteNoteList.get(0);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if(text.equalsIgnoreCase("yes")){
            try {
                Connection connection = DatabaseConnector.getConnection();
                String sql = "DELETE FROM notes WHERE to_char(note_date, 'YYYY-MM-DD') = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, date);
                statement.executeUpdate();
                message.setText("Note deleted.");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else {
            message.setText("Deletion canceled");
        }

        NoteWise.statement = Statements.WAITING;
        return message;
    }
}
