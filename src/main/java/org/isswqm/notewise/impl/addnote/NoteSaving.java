package org.isswqm.notewise.impl.addnote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class NoteSaving implements Command {

    private final Connection connection;

    public NoteSaving() throws SQLException {
        connection = DatabaseConnector.getConnection();
    }

    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if(text.equalsIgnoreCase("yes")){
            String sql = "INSERT INTO notewise_db.public.notes (chat_id, message, note_date) VALUES (?, ?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, Long.parseLong(NoteWise.noteInfoMap.get("ChatId")));
                statement.setString(2, NoteWise.noteInfoMap.get("Text"));
                statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                statement.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
            message.setText("Note saved!");
        }else {
            message.setText("Adding note canceled.");
        }
        NoteWise.statement = Statements.WAITING;
        return message;
    }
}

