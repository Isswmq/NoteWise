package org.isswqm.notewise.command;

import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.config.NoteConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class NoteCommand {

    private final Connection connection;

    public NoteCommand() throws SQLException {
        connection = DatabaseConnector.getConnection();
    }

    public void saveNote(NoteConfig note){
        String sql = "INSERT INTO notewise_db.public.reminders (chat_id, message, data) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, note.getChatId());
            statement.setString(2, note.getMessage());
            statement.setTimestamp(3, Timestamp.valueOf(note.getDateTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
