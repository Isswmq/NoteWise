package org.isswqm.notewise.command;

import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Remind implements Runnable, Command {

    private final Connection connection;
    private final AbsSender bot;

    public Remind(AbsSender bot) throws SQLException {
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
                System.out.println(date);

                String sql = "SELECT chat_id, message, reminder_date FROM notewise_db.public.reminders WHERE to_char(reminder_date, 'yyyy-MM-dd HH24:MI') = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, date);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    bot.execute(execute(String.valueOf(resultSet.getLong(1)), "Напоминание!!! " + resultSet.getString(2)));
                    Thread.currentThread().interrupt();
                }
                try{
                    Thread.sleep(10_000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }catch (SQLException e){
                e.printStackTrace();
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @Override
    public SendMessage execute(String chatId, String text) {
        return new SendMessage(chatId, text);
    }
}
