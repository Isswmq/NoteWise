package org.isswqm.notewise.impl.deleteremind;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemindDeleted implements Command {

    @Override
    public SendMessage execute(String chatId, String text)  {
        String date = NoteWise.deleteRemindMap.get("Date");
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if(text.equalsIgnoreCase("yes")){
            try {
                Connection connection = DatabaseConnector.getConnection();
                String sql = "DELETE FROM notewise_db.public.reminders WHERE to_char(reminder_date, 'yyyy-MM-dd HH24:MI') = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, date);
                statement.executeUpdate();
                message.setText("Reminder deleted.");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else {
            message.setText("Deletion canceled.");
            if (!NoteWise.checkButtonPressAndSetStatement(text)){
                message.setText("Please select the function again");
            }
            return message;
        }

        NoteWise.statement = Statements.WAITING;
        return message;
    }
}
