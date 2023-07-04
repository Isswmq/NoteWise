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
        String date = NoteWise.deleteRemindList.get(0);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if(text.equalsIgnoreCase("да")){
            try {
                Connection connection = DatabaseConnector.getConnection();
                String sql = "DELETE FROM reminders WHERE to_char(reminder_date, 'YYYY-MM-DD') = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, date);
                statement.executeUpdate();
                message.setText("Напоминание удалено");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else {
            message.setText("Удаление отменено");
        }

        NoteWise.statement = Statements.WAITING;
        return message;
    }
}
