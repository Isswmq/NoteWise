package org.isswqm.notewise.impl.reminders;

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
import java.time.format.DateTimeFormatter;

public class RemindIsSaving implements Command {

    private final Connection connection;

    public RemindIsSaving() throws SQLException {
        connection = DatabaseConnector.getConnection();
    }

    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if(text.equalsIgnoreCase("yes")){
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime date = LocalDateTime.parse(NoteWise.reminderInfoList.get(2), formatter);
                String sql = "INSERT INTO notewise_db.public.reminders (chat_id, message, reminder_date) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, Long.parseLong(NoteWise.reminderInfoList.get(0)));
                statement.setString(2, NoteWise.reminderInfoList.get(1));
                statement.setTimestamp(3, Timestamp.valueOf(date));
                statement.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
            message.setText("Reminder saved.");
        }else {
            message.setText("Save canceled.");
        }

        NoteWise.reminderInfoList.clear();
        NoteWise.statement = Statements.WAITING;
        return message;
    }
}
