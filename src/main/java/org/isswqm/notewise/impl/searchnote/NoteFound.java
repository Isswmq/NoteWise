package org.isswqm.notewise.impl.searchnote;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteFound implements Command {
    private final Connection connection;

    public NoteFound() throws SQLException {
        connection = DatabaseConnector.getConnection();
    }

    @Override
    public SendMessage execute(String chatId, String text){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("что-то пошло не так");
        StringBuilder builder = new StringBuilder();
        if(text.equalsIgnoreCase("да")){
            try{
                String sql = "SELECT message FROM notewise_db.public.notes WHERE chat_id = ? AND to_char(date, 'YYYY-MM-DD') = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, (Long.parseLong(chatId)));
                statement.setString(2, NoteWise.searchNoteList.get(0));
                ResultSet resultSet = statement.executeQuery();
                int counter = 1;
                while(resultSet.next()){
                    builder.append("message: ").append(resultSet.getString(counter));
                    counter++;
                }
                NoteWise.searchNoteList.clear();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else {
            message.setText("Просмотр заметки отменен");
        }

        if(!builder.isEmpty()){
            message.setText(builder.toString());
        }
        NoteWise.statement = Statements.WAITING;
        return message;
    }
}
