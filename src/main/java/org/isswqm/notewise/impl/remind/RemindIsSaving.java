package org.isswqm.notewise.impl.remind;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.helper.ReminderHelper;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.SQLException;

public class RemindIsSaving implements Command {
    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if(text.equalsIgnoreCase("да")){
            try {
                ReminderHelper helper = new ReminderHelper();
                helper.remind(NoteWise.reminderInfoList);
            }catch (SQLException e){
                e.printStackTrace();
            }
            message.setText("Напоминание сохранено");
        }else {
            message.setText("Сохранение отменено");
        }

        NoteWise.reminderInfoList.clear();
        NoteWise.statement = Statements.WAITING;
        return message;
    }
}
