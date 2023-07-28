package org.isswqm.notewise.impl.deleteremind;

import org.isswqm.notewise.NoteWise;
import org.isswqm.notewise.config.Statements;
import org.isswqm.notewise.impl.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class WaitingForDeleteRemind implements Command {

    @Override
    public SendMessage execute(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        if (!NoteWise.checkButtonPressAndSetStatement(text)){
            message.setText("Please select the function again");
        }else {
            NoteWise.deleteRemindMap.put("Date", text);
            message.setText("Delete the remind? <yes>");
            NoteWise.statement = Statements.REMIND_DELETED;
        }
        return message;
    }
}
