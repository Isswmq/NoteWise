package org.isswqm.notewise.command;

import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.config.ReminderConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RemindCommand {
    private final Connection connection;

    public RemindCommand() throws SQLException {
        connection = DatabaseConnector.getConnection();
    }
    public void saveRemind(ReminderConfig reminder) {
        String sql = "INSERT INTO notewise_db.public.reminders (chat_id, message, reminder_date) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, reminder.getChatId());
            statement.setString(2, reminder.getMessage());
            statement.setTimestamp(3, Timestamp.valueOf(reminder.getDatetime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
