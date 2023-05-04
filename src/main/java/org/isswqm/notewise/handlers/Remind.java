package org.isswqm.notewise.handlers;

import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.config.ReminderConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Remind {
    private Connection connection;

    public Remind() throws SQLException {
        connection = DatabaseConnector.getConnection();
    }
    public void saveRemind(ReminderConfig reminder) {
        String sql = "INSERT INTO reminder.public.reminders (chat_id, message, data) VALUES (?, ?, ?)";
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
