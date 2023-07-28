package org.isswqm.notewise.impl.viewnotes;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.DatabaseConnector;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ViewNoteCommand implements Command {

    private final Connection connection;

    public ViewNoteCommand() throws SQLException {
        connection = DatabaseConnector.getConnection();
    }
    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        NoteWise.statement = Statements.WAITING;
        LinkedHashMap<String, List<String>> messagesMap = new LinkedHashMap<>();
        if (text.equalsIgnoreCase("yes")) {
            try {
                String sql = "SELECT message, note_date from notewise_db.public.notes WHERE chat_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, Long.parseLong(chatId));
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String messageText = resultSet.getString(1);
                    String year = String.valueOf(resultSet.getTimestamp(2).toLocalDateTime().getYear());
                    String month = "0" + (resultSet.getTimestamp(2).toLocalDateTime().getMonth().getValue());
                    String day = String.valueOf(resultSet.getTimestamp(2).toLocalDateTime().getDayOfMonth());
                    String hours = String.valueOf(resultSet.getTimestamp(2).toLocalDateTime().getHour());
                    String minutes = String.valueOf(resultSet.getTimestamp(2).toLocalDateTime().getMinute());
                    String seconds = String.valueOf(resultSet.getTimestamp(2).toLocalDateTime().getSecond());
                    String value = "Message text: " + messageText + "\n" + "Date Time: "
                            + year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds + "\n";
                    if (!messagesMap.containsKey(chatId)) {
                        messagesMap.put(chatId, new ArrayList<>());
                    }
                    messagesMap.get(chatId).add(value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            message.setText("Displaying notes canceled.");
            if (!NoteWise.checkButtonPressAndSetStatement(text)){
                message.setText("Please select the function again");
            }
            return message;
        }

        StringBuilder builder = new StringBuilder();
        if (messagesMap.containsKey(chatId)) {
            for (String messageFromMessageMap : messagesMap.get(chatId)) {
                builder.append(messageFromMessageMap);
                builder.append("\n");
            }
        }

        if(builder.isEmpty()){
            message.setText("No notes found.");
        }else {
            message.setText(builder.toString());
        }

        return message;
    }
}
