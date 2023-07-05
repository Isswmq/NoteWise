package org.isswqm.notewise.impl.reminders;

import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RemindChecker implements Runnable, Command {

    private final Connection connection;
    private final AbsSender bot;

    public RemindChecker(AbsSender bot) throws SQLException {
        connection = DatabaseConnector.getConnection();
        this.bot = bot;
    }
    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String date = now.format(formatter);
                String sql = "SELECT chat_id, message, reminder_date FROM notewise_db.public.reminders WHERE to_char(reminder_date, 'yyyy-MM-dd HH24:MI') = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, date);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    bot.execute(execute(String.valueOf(resultSet.getLong(1)), "Reminder! +" +
                            "Text: " + resultSet.getString(2)));
                    Thread.currentThread().interrupt();
                }
                try{
                    Thread.sleep(10_000);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }catch (SQLException  | TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public SendMessage execute(String chatId, String text) {
        return new SendMessage(chatId, text);
    }
}
