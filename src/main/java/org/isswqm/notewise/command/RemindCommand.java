package org.isswqm.notewise.command;

import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.config.ReminderConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class RemindCommand {
    private final Connection connection;

    public RemindCommand() throws SQLException {
        connection = DatabaseConnector.getConnection();
    }
    public void saveRemind(ReminderConfig reminder) {
        String sql = "INSERT INTO reminder.public.reminders (chat_id, message, data) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, reminder.getChatId());
            statement.setString(2, reminder.getMessage());
            statement.setTimestamp(3, Timestamp.valueOf(reminder.getDatetime().truncatedTo(ChronoUnit.MINUTES)));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
